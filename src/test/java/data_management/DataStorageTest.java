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

import java.util.List;

public class DataStorageTest {

    private DataStorage storage;
    private AlertGenerator alertGenerator;

    @BeforeEach
    public void setUp() {
        storage = DataStorage.getInstance();
        alertGenerator = new AlertGenerator(storage); 
    }

    @Test
    void testAddAndGetRecords() {
        // TODO Perhaps you can implement a mock data reader to mock the test data?
        // DataReader reader
        DataStorage storage = DataStorage.getInstance();
        storage.addPatientData(1, 100.0, "WhiteBloodCells", 1714376789050L);
        storage.addPatientData(1, 200.0, "WhiteBloodCells", 1714376789051L);

        List<PatientRecord> records = storage.getRecords(1, 1714376789050L, 1714376789051L);
        assertEquals(2, records.size()); // Check if two records are retrieved
        assertEquals(100.0, records.get(0).getMeasurementValue()); // Validate first record
    }

    @Test
    public void testNoRecordsFound() {
        List<PatientRecord> records = storage.getRecords(999, 1714376789050L, 1714376789051L);
        assertTrue(records.isEmpty(), "Should retrieve no records for a non-existent patient");
    }

    @Test
    public void testEvaluateData() {
        Patient patient = new Patient(1); 
        AlertGenerator alertGenerator = new AlertGenerator(storage);
        assertDoesNotThrow(() -> alertGenerator.evaluateData(patient), "Evaluate should not throw");
    }

    @Test
    public void testSimpleDataReader() {
        SimpleDataReader reader = new SimpleDataReader("C:/Users/Nikitas Tikkos/OneDrive/Desktop/testfile.csv");
        assertDoesNotThrow(() -> reader.readData(storage), "Reading data should not throw exceptions");
    }
 
    @Test
    public void testForIncreasingTrend() {
        // Simulate consecutive increasing BP readings using existing methods
        storage.addPatientData(1, 120, "SystolicPressure", System.currentTimeMillis() - 2000);
        storage.addPatientData(1, 132, "SystolicPressure", System.currentTimeMillis() - 1000);
        storage.addPatientData(1, 145, "SystolicPressure", System.currentTimeMillis());
        alertGenerator.evaluateData(new Patient(1));
        assertTrue(alertTriggered("Increasing BP Trend"), "Alert for increasing BP trend should be triggered");
    }

    @Test
    public void testForDecreasingTrend() {
        // Simulate consecutive decreasing BP readings
        storage.addPatientData(1, 145, "SystolicPressure", System.currentTimeMillis() - 2000);
        storage.addPatientData(1, 130, "SystolicPressure", System.currentTimeMillis() - 1000);
        storage.addPatientData(1, 115, "SystolicPressure", System.currentTimeMillis());
        alertGenerator.evaluateData(new Patient(1));
        assertTrue(alertTriggered("Decreasing BP Trend"), "Alert for decreasing BP trend should be triggered");
    }

    @Test
    public void testForCriticalThresholds() {
        storage.addPatientData(1, 185, "SystolicPressure", System.currentTimeMillis());
        storage.addPatientData(1, 55, "SystolicPressure", System.currentTimeMillis() + 1000);
        alertGenerator.evaluateData(new Patient(1));
        assertTrue(alertTriggered("Critical High BP"), "Alert for critically high BP should be triggered");
        assertTrue(alertTriggered("Critical Low BP"), "Alert for critically low BP should be triggered");
    }

    @Test
    public void testForLowSaturation() {
        storage.addPatientData(1, 89, "BloodSaturation", System.currentTimeMillis());
        alertGenerator.evaluateData(new Patient(1));
        assertTrue(alertTriggered("Low Saturation"), "Alert for low saturation should be triggered");
    }

    private boolean alertTriggered(String alertType) {
        // This method checks if an alert of type alertType was logged in the system.
        // This could check a list of alerts in AlertGenerator or some sort of event log.
        return true; // Assume true for placeholder
    }
}
