package com.alerts;

public class ECGAlertFactory extends AlertFactory {
    @Override
    public Alert createAlert(String patientId, String condition) {
        return new ECGAlert(patientId, condition, System.currentTimeMillis());
    }
}
