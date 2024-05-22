package com.data_management;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * The {@code SimpleDataReader} class implements the {@link DataReader} interface
 * to read patient data from a simple text file. Each line of the file represents
 * a patient record with fields separated by commas in the following order:
 * patientId, measurementValue, recordType, timestamp.
 */

public class SimpleDataReader implements DataReader {

    private String filePath;

/**
     * Constructs a {@code SimpleDataReader} with the specified file path.
     *
     * @param filePath the path to the file containing patient data
     */

    public SimpleDataReader(String filePath) {
        this.filePath = filePath;
    }

/**
     * Reads patient data from the specified file and adds it to the provided
     * {@link DataStorage} instance.
     *
     * @param dataStorage the {@code DataStorage} instance to which the read data
     *                    will be added
     * @throws IOException if an I/O error occurs while reading the file
     */


    @Override
    public void readData(DataStorage dataStorage) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            int patientId = Integer.parseInt(parts[0]);
            double measurementValue = Double.parseDouble(parts[1]);
            String recordType = parts[2];
            long timestamp = Long.parseLong(parts[3]);
            dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
        }
        reader.close();
    }

@Override
    public void connectToWebSocket(String url, DataStorage dataStorage) throws IOException {
         // Connect to the WebSocket server to handle real-time data
        try {
            URI uri = new URI(url);
            WebSocketClientImpl client = new WebSocketClientImpl(uri, dataStorage);
            client.connectBlocking(); // Blocking connect to ensure connection is established
        } catch (URISyntaxException | InterruptedException e) {
            // Handle connection errors
            throw new IOException("Failed to connect to WebSocket", e);
        }
    }
    
}
