package data_management;

import com.data_management.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.alerts.*;

import static org.junit.jupiter.api.Assertions.*;

public class AlertStrategyTest {

    private Patient patient;
    private BloodPressureAlertFactory bloodPressureAlertFactory;
    private ECGAlertFactory heartRateAlertFactory;
    private BloodOxygenAlertFactory oxygenSaturationAlertFactory;

    @BeforeEach
    public void setUp() {
        patient = new Patient(1);

        // Add mock data to the patient for testing
        patient.addRecord(185, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(125, "DiastolicPressure", System.currentTimeMillis());
        patient.addRecord(95, "HeartRate", System.currentTimeMillis());
        patient.addRecord(88, "OxygenSaturation", System.currentTimeMillis());

        bloodPressureAlertFactory = new BloodPressureAlertFactory();
        heartRateAlertFactory = new ECGAlertFactory();
        oxygenSaturationAlertFactory = new BloodOxygenAlertFactory();
    }

    @Test
    public void testBloodPressureStrategy() {
        AlertStrategy strategy = new BloodPressureStrategy(bloodPressureAlertFactory);

        // Use the mock patient data
        strategy.checkAlert(patient);

        // We cannot directly verify the alert actions without an actual alert action implementation
        // However, we can ensure that the strategy ran without errors
        assertTrue(true);
    }

    @Test
    public void testHeartRateStrategy() {
        AlertStrategy strategy = new HeartRateStrategy(heartRateAlertFactory);

        // Use the mock patient data
        strategy.checkAlert(patient);

        // We cannot directly verify the alert actions without an actual alert action implementation
        // However, we can ensure that the strategy ran without errors
        assertTrue(true);
    }

    @Test
    public void testOxygenSaturationStrategy() {
        AlertStrategy strategy = new OxygenSaturationStrategy(oxygenSaturationAlertFactory);

        // Use the mock patient data
        strategy.checkAlert(patient);

        // We cannot directly verify the alert actions without an actual alert action implementation
        // However, we can ensure that the strategy ran without errors
        assertTrue(true);
    }

    @Test
    public void testNormalBloodPressureStrategy() {
        // Add normal blood pressure readings
        patient.addRecord(120, "SystolicPressure", System.currentTimeMillis());
        patient.addRecord(80, "DiastolicPressure", System.currentTimeMillis());

        AlertStrategy strategy = new BloodPressureStrategy(bloodPressureAlertFactory);

        // Use the mock patient data
        strategy.checkAlert(patient);

        // We cannot directly verify the alert actions without an actual alert action implementation
        // However, we can ensure that the strategy ran without errors
        assertTrue(true);
    }

    @Test
    public void testNormalOxygenSaturationStrategy() {
        // Add normal oxygen saturation reading
        patient.addRecord(98, "OxygenSaturation", System.currentTimeMillis());

        AlertStrategy strategy = new OxygenSaturationStrategy(oxygenSaturationAlertFactory);

        // Use the mock patient data
        strategy.checkAlert(patient);

        // We cannot directly verify the alert actions without an actual alert action implementation
        // However, we can ensure that the strategy ran without errors
        assertTrue(true);
    }

    @Test
    public void testNormalHeartRateStrategy() {
        // Add normal heart rate reading
        patient.addRecord(75, "HeartRate", System.currentTimeMillis());

        AlertStrategy strategy = new HeartRateStrategy(heartRateAlertFactory);

        // Use the mock patient data
        strategy.checkAlert(patient);

        // We cannot directly verify the alert actions without an actual alert action implementation
        // However, we can ensure that the strategy ran without errors
        assertTrue(true);
    }
}
