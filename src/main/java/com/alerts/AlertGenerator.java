package com.alerts;

import java.util.List;

import com.data_management.DataStorage;
import com.data_management.Patient;
import com.data_management.PatientRecord;

/**
 * The {@code AlertGenerator} class is responsible for monitoring patient data
 * and generating alerts when certain predefined conditions are met. This class
 * relies on a {@link DataStorage} instance to access patient data and evaluate
 * it against specific health criteria.
 */
public class AlertGenerator {
    private DataStorage dataStorage;

    /**
     * Constructs an {@code AlertGenerator} with a specified {@code DataStorage}.
     * The {@code DataStorage} is used to retrieve patient data that this class
     * will monitor and evaluate.
     *
     * @param dataStorage the data storage system that provides access to patient
     *                    data
     */
    public AlertGenerator(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Evaluates the specified patient's data to determine if any alert conditions
     * are met. If a condition is met, an alert is triggered via the
     * {@link #triggerAlert}
     * method. This method should define the specific conditions under which an
     * alert
     * will be triggered.
     *
     * @param patient the patient data to evaluate for alert conditions
     */
    public void evaluateData(Patient patient) {
        List<PatientRecord> records = dataStorage.getRecords(patient.getPatientId(), 1700000000000L, 1800000000000L);
        checkBloodPressure(records, patient);
        for (PatientRecord record : records) {
            if ("BloodSaturation".equals(record.getRecordType())) {
                checkBloodSaturation(record, patient);
            }
            if ("ECG".equals(record.getRecordType())) {
                checkECG(record, patient);
            }
            if ("BloodLevels".equals(record.getRecordType())){
                checkBloodLevels(records, patient);
            }
        }
    }

    // Check conditions for blood pressure and trigger alerts if needed
    private void checkBloodPressure(List<PatientRecord> records, Patient patient) {
        double systolic = 0;
        double diastolic = 0;
        boolean systolicSet = false;
        boolean diastolicSet = false;

        // Iterate through records to find systolic and diastolic pressures
        for (PatientRecord record : records) {
            if ("SystolicPressure".equals(record.getRecordType())) {
                systolic = record.getMeasurementValue();
                systolicSet = true;
            } else if ("DiastolicPressure".equals(record.getRecordType())) {
                diastolic = record.getMeasurementValue();
                diastolicSet = true;
            }

            // If both systolic and diastolic pressures are set, check against threshold values
            if (systolicSet && diastolicSet) {
                if (systolic > 180 || systolic < 90 || diastolic > 120 || diastolic < 60) {
                    // Trigger alert for critical blood pressure
                    triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "Critical Blood Pressure", System.currentTimeMillis()));
                }
            }
        }
    }
    
    // Check conditions for blood saturation and trigger alerts if needed
    private void checkBloodSaturation(PatientRecord record, Patient patient) {
        double saturation = record.getMeasurementValue();
        if (saturation < 92) {
            triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "Low Blood Saturation", record.getTimestamp()));
        }
    }

    // Check conditions for ECG and trigger alerts if needed
    private void checkECG(PatientRecord record, Patient patient) {
        double ecgValue = record.getMeasurementValue();
        if (ecgValue < 50 || ecgValue > 100) { 
            triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "Abnormal ECG", record.getTimestamp()));
        }
    }

     // Check conditions for blood levels and trigger alerts if needed
    private void checkBloodLevels(List<PatientRecord> records, Patient patient) {
        for (PatientRecord record : records) {
            if ("Cholesterol".equals(record.getRecordType())) {
                double cholesterol = record.getMeasurementValue();
                if (cholesterol > 240) { // High cholesterol alert threshold
                    triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "High Cholesterol", record.getTimestamp()));
                }
            } else if ("WhiteBloodCells".equals(record.getRecordType())) {
                double whiteCells = record.getMeasurementValue();
                if (whiteCells < 4 || whiteCells > 11) { // Abnormal white blood cells count
                    triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "Abnormal White Blood Cells Count", record.getTimestamp()));
                }
            } else if ("RedBloodCells".equals(record.getRecordType())) {
                double redCells = record.getMeasurementValue();
                if (redCells < 4.5 || redCells > 6.0) { // Abnormal red blood cells count
                    triggerAlert(new Alert(Integer.toString(patient.getPatientId()), "Abnormal Red Blood Cells Count", record.getTimestamp()));
                }
            }
        }
    }
    

    /**
     * Triggers an alert for the monitoring system. This method can be extended to
     * notify medical staff, log the alert, or perform other actions. The method
     * currently assumes that the alert information is fully formed when passed as
     * an argument.
     *
     * @param alert the alert object containing details about the alert condition
     */
    private void triggerAlert(Alert alert) {
        // Implementation might involve logging the alert or notifying staff
        System.out.println("Alert triggered: " + alert.getCondition() + " for Patient ID: " + alert.getPatientId());
    }
}
