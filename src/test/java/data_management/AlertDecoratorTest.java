package data_management;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.alerts.*;

public class AlertDecoratorTest {

    @Test
    public void testRepeatedAlertDecorator() {
        Alert alert = new BloodPressureAlert("1", "High Blood Pressure", System.currentTimeMillis());
        Alert decoratedAlert = new RepeatedAlertDecorator(alert);
        assertEquals("High Blood Pressure", decoratedAlert.getCondition());
        decoratedAlert.alertAction();
    }

    @Test
    public void testPriorityAlertDecorator() {
        Alert alert = new BloodOxygenAlert("1", "Low Blood Oxygen", System.currentTimeMillis());
        Alert decoratedAlert = new PriorityAlertDecorator(alert);
        assertEquals("Low Blood Oxygen", decoratedAlert.getCondition());
        decoratedAlert.alertAction();
    }
}
