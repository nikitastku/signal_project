package com.cardio_generator.generators;

import java.util.Random;

import com.cardio_generator.outputs.OutputStrategy;

/**
 * Generates alert conditions for patients as part of the health data simulation
 * Alerts are randomly triggered and resolved based on predefined probabilities
 */

public class AlertGenerator implements PatientDataGenerator {

    public static final Random randomGenerator = new Random();
    //Changed the variable name in camelCase
    private boolean[] alertStates; // false = resolved, true = pressed, 

/**
 * Constructs an AlertGenerator for a specified number of patients
 * 
 * @param patientCount the number of patients to manage alert states for
 */

    public AlertGenerator(int patientCount) {
        alertStates = new boolean[patientCount + 1];
    }

/**
 * Generates and outputs alert conditions for a specific patient. The method simulates the triggering
 * and resolving of alerts based on random probabilities
 * 
 * @param patientId the identifier for the patient to generate alerts for
 * @param outputStrategy the strategy for outputting the alert status
 */

    @Override
    public void generate(int patientId, OutputStrategy outputStrategy) {
        try {
            if (alertStates[patientId]) {
                if (randomGenerator.nextDouble() < 0.9) { // 90% chance to resolve
                    alertStates[patientId] = false;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "resolved");
                }
            } else {
                //Changed the variable name to camelCase
                double lambda = 0.1; // Average rate (alerts per period), adjust based on desired frequency
                double p = -Math.expm1(-lambda); // Probability of at least one alert in the period
                boolean alertTriggered = randomGenerator.nextDouble() < p;

                if (alertTriggered) {
                     alertStates[patientId] = true;
                    // Output the alert
                    outputStrategy.output(patientId, System.currentTimeMillis(), "Alert", "triggered");
                }
            }
        } catch (Exception e) {
            System.err.println("Error generating alert " + e.getMessage()); //Changed the exception messge, catch blocks should log the exception, consider alertStates more specific exception and add alertStates comment rather than just printing the stack trace
            e.printStackTrace();
        }
    }
}
