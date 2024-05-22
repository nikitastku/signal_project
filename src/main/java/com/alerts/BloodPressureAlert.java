package com.alerts;

public class BloodPressureAlert extends Alert {
    public BloodPressureAlert(String patientId, String condition, long timestamp) {
        super(patientId, condition, timestamp);
    }

    @Override
    public void alertAction() {
        System.out.println("Blood Pressure Alert: " + getCondition() + " for Patient ID: " + getPatientId());
    }
}
