package data_management;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.alerts.Alert;
import com.alerts.AlertGenerator;
import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;
import com.data_management.SimpleDataReader;
import com.data_management.WebSocketClientImpl;

import java.util.List;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketTest {

    private DataStorage storage;
    private WebSocketClientImpl client;
    private SimpleDataReader dataReader;

    @BeforeEach
    public void setUp() {
        storage = new DataStorage();
        try {
            client = new WebSocketClientImpl(new URI("ws://localhost:8080"), storage);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testWebSocketClientOnMessage() throws Exception {
        client.connectBlocking();

        // Simulate receiving messages from the WebSocket server
        client.onMessage("1,100.0,WhiteBloodCells,1714376789050");
        client.onMessage("1,200.0,WhiteBloodCells,1714376789051");

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size());
        assertEquals(100.0, records.get(0).getMeasurementValue(), 0.01);
        assertEquals(200.0, records.get(1).getMeasurementValue(), 0.01);

        client.close();
    }

    @Test
    public void testOnMessage_invalidMessage() {
        String message = "1,invalid,HeartRate,1714376789050";
        client.onMessage(message);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(0, records.size());
    }

    @Test
    public void testOnOpen() {
        client.onOpen(null);
        assertTrue(client.isOpen());
    }

    @Test
    public void testOnClose() {
        client.onClose(0, "Test Close", false);
        assertFalse(client.isOpen());
    }

    @Test
    public void testOnError() {
        client.onError(new Exception("Test Exception"));
    }

    @Test
    public void testConnectToWebSocket_success() throws IOException, URISyntaxException {
        String webSocketUrl = "ws://localhost:8080";
        SimpleDataReader dataReader = new SimpleDataReader("dummyFilePath");
        assertDoesNotThrow(() -> dataReader.connectToWebSocket(webSocketUrl, storage));
    }

    @Test
    public void testConnectToWebSocket_invalidUrl() {
        String webSocketUrl = "ws://invalid_url";
        assertThrows(IOException.class, () -> dataReader.connectToWebSocket(webSocketUrl, storage));
    }

    @Test
    public void testRealTimeDataProcessing() throws InterruptedException {
        client.connectBlocking();
        client.onMessage("1,100.0,HeartRate,1714376789050");
        client.onMessage("1,110.0,HeartRate,1714376789051");
        client.onMessage("1,95.0,BloodPressure,1714376789052");

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789053L);
        assertEquals(3, records.size());

        AlertGenerator alertGenerator = new AlertGenerator(storage);
        alertGenerator.evaluateData(new Patient(1));
        
    }

    @Test
    public void testConnectionLoss() throws InterruptedException {
        client.connectBlocking();
        client.close(); // Simulate connection loss
        assertFalse(client.isOpen());
    }

    @Test
    public void testReconnect() throws InterruptedException {
        client.connectBlocking();
        client.close(); // Simulate connection loss
        assertFalse(client.isOpen());

        client.reconnectBlocking(); // Reconnect
        assertTrue(client.isOpen());
    }

    @Test
    public void testDataTransmissionFailure() {
        client.onMessage("invalid message format");

        // Check if no data is added to the storage due to invalid message format
        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(0, records.size());
    }
    
}
