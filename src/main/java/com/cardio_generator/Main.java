package com.cardio_generator;

import java.io.IOException;

import com.data_management.DataStorage;

public class Main {
    public static void main(String[] args) throws IOException {
   if (args.length > 0 && args[0].equals("DataStorage")) {
    DataStorage.main(new String[]{});
    } else {
    HealthDataSimulator.main(new String[]{});
    }
    }
}
