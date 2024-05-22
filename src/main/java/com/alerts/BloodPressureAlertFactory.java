package com.alerts;

public class BloodPressureAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition) {
        return new BloodPressureAlert(patientId, condition, System.currentTimeMillis());
    }
}
