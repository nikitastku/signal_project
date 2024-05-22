package com.alerts;

public class BloodOxygenAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition) {
        return new BloodOxygenAlert(patientId, condition, System.currentTimeMillis());
    }
}
