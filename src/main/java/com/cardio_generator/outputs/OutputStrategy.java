package com.cardio_generator.outputs;

/**
 * Interface for output strategies used in the health data simulation
 * This interface defines a method for outputting patient data in a standardized way,
 * allowing different implementations to handle how data is presented or stored
 */

public interface OutputStrategy {

/**
 * Outputs data for a specific patient at a given time
 * 
 * @param patientId The identifier for the patient to whom the data pertains
 * @param timestamp The time at which the data is recorded, represented as a long
 * @param label A string label describing the type of data (eg, Heart Rate)
 * @param data The actual data value as a string
 */

    void output(int patientId, long timestamp, String label, String data);
}
