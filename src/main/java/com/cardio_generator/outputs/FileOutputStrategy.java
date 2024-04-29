package com.cardio_generator.outputs;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of {@link OutputStrategy} that writes output data to files
 * This class manages file creation and writing in a specified base directory
 */

//Renamed the class to start with an uppercase letter
public class FileOutputStrategy implements OutputStrategy {

    private String baseDirectory;

    /**
     * A map of file paths associated with data labels to ensure each data type is written to a consistent file
     */

    //Changed the final variable to UPPER_SNAKE_CASE due to it being a constant
    public final ConcurrentHashMap<String, String> FILE_MAP = new ConcurrentHashMap<>();

    /**
     * Constructs a new {@code FileOutputStrategy} with a specified base directory for file output
     * 
     * @param baseDirectory The directory in which data files will be created and managed
     */

    //Changed the constructor name starting with an uppercase letter
    public FileOutputStrategy(String baseDirectory) {
        //Changed the variable name to camelCase to align with Google Java Style Guide
        this.baseDirectory = baseDirectory;
    }

    /**
     * Outputs data to a file specific to the data label. If the file doesn't exist, it is created
     * Each output entry is formatted and appended to the appropriate file
     * 
     * @param patientId The identifier for the patient to whom the data pertains
     * @param timestamp The time at which the data is recorded, in milliseconds
     * @param label A descriptive label for the data, used to determine the output file
     * @param data The actual data value as a string
     */

    @Override
    public void output(int patientId, long timestamp, String label, String data) {
        try {
            // Create the directory
            Files.createDirectories(Paths.get(baseDirectory));
        } catch (IOException e) {
            System.err.println("Error creating base directory: " + e.getMessage());
            return;
        }
        // Set the filePath variable, changed the variable to camelCase
        String filePath = FILE_MAP.computeIfAbsent(label, k -> Paths.get(baseDirectory, label + ".txt").toString());

        // Write the data to the file
        try (PrintWriter out = new PrintWriter(
                Files.newBufferedWriter(Paths.get(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND))) {
            out.printf("Patient ID: %d, Timestamp: %d, Label: %s, Data: %s%n", patientId, timestamp, label, data);
        } catch (Exception e) {
            System.err.println("Error writing to file " + filePath + ": " + e.getMessage());
        }
    }
}
