package com.cardio_generator.generators;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Interface for generating patient data
 * Implementation of this interface should provide mechanisms to generate and output patient-specific data
 */

public interface PatientDataGenerator {

    /**
     * Generates and outputs data for a specific patient
     * 
     * @param patientId The unique identifier for the patient for whom data is to be generated
     * @param outputStrategy The output strategy to be used for displaying or storing the generated data
     */
    void generate(int patientId, OutputStrategy outputStrategy);
}
