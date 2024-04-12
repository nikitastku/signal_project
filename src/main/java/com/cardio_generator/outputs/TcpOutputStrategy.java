package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

/**
 * Implements a {@link OutputStrategy} that sends output data to a client over TCP
 * This class manages the server socket connection and sends formatted data strings
 */

public class TcpOutputStrategy implements OutputStrategy {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;

/**
 * Constructs a TCP output strategy that listens for client connections on the specified port
 * Initializes a server socket and accepts client connections in a new thread
 * 
 * @param port The port number on which the server will listen for connections
 */

    public TcpOutputStrategy(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("TCP Server started on port " + port);

            // Accept clients in a new thread to not block the main thread
            Executors.newSingleThreadExecutor().submit(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    out = new PrintWriter(clientSocket.getOutputStream(), true);
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends data to the connected client over TCP
     * The data is formatted as a string and sent only if a client is connected
     * 
     * @param patientId the identifier for the patient to whom the data pertains
     * @param timestamp The time at which the data was recorded, in milliseconds
     * @param label A descreptive label for the data (eg Heart Rate)
     * @param data The actual data value as a string
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        if (out != null) {
            String message = String.format("%d,%d,%s,%s", patientId, timestamp, label, data);
            out.println(message);
        }
    }
}
