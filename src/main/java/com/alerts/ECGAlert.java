package com.alerts;

public class ECGAlert extends Alert {
    public ECGAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public void alertAction() {
        System.out.println("ECG Alert: " + getCondition() + " for Patient ID: " + getPatientId());
    }
}
