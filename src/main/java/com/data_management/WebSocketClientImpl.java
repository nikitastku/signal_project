package com.data_management;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * The {@code WebSocketClientImpl} class extends the {@link WebSocketClient} class
 * to connect to a WebSocket server and receive patient data in real-time.
 */

public class WebSocketClientImpl extends WebSocketClient {

    private DataStorage dataStorage;

    /**
     * Constructs a {@code WebSocketClientImpl} with the specified server URI and data storage.
     *
     * @param serverUri the URI of the WebSocket server
     * @param dataStorage the data storage where received data will be stored
     */

    public WebSocketClientImpl(URI serverUri, DataStorage dataStorage) {
        super(serverUri);
        this.dataStorage = dataStorage;
    }

/**
     * Handles WebSocket connection opening by printing a message.
     *
     * @param handshakedata the handshake data
     */

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to WebSocket server.");
    }

    /**
     * Handles incoming messages from the WebSocket server by parsing the message
     * and adding the data to the data storage.
     *
     * @param message the message received from the server
     */

    @Override
    public void onMessage(String message) {
        // Parse message and add to dataStorage
        String[] parts = message.split(",");
        if (parts.length == 4) {
            try {
                int patientId = Integer.parseInt(parts[0]);
                double measurementValue = Double.parseDouble(parts[1]);
                String recordType = parts[2];
                long timestamp = Long.parseLong(parts[3]);
                dataStorage.addPatientData(patientId, measurementValue, recordType, timestamp);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing message: " + message);
            }
        } else {
            System.err.println("Invalid message format: " + message);
        }
    }

    /**
     * Handles WebSocket connection closure by printing the reason for disconnection.
     *
     * @param code the status code of the close
     * @param reason the reason for the close
     * @param remote whether the close was initiated by the remote host
     */

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Disconnected from WebSocket server: " + reason);
    }

    /**
     * Handles WebSocket errors by printing the error message.
     *
     * @param ex the exception that occurred
     */

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket error: " + ex.getMessage());
    }
}
