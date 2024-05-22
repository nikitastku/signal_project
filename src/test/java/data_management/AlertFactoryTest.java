package data_management;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.alerts.*;

public class AlertFactoryTest {

    @Test
    public void testBloodPressureAlertFactory() {
        AlertFactory factory = new BloodPressureAlertFactory();
        Alert alert = factory.createAlert("1", "High Blood Pressure");
        assertNotNull(alert);
        assertTrue(alert instanceof BloodPressureAlert);
    }

    @Test
    public void testBloodOxygenAlertFactory() {
        AlertFactory factory = new BloodOxygenAlertFactory();
        Alert alert = factory.createAlert("1", "Low Blood Oxygen");
        assertNotNull(alert);
        assertTrue(alert instanceof BloodOxygenAlert);
    }

    @Test
    public void testECGAlertFactory() {
        AlertFactory factory = new ECGAlertFactory();
        Alert alert = factory.createAlert("1", "Irregular ECG");
        assertNotNull(alert);
        assertTrue(alert instanceof ECGAlert);
    }
}
