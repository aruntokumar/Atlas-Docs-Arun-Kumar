package org.example;

import java.util.Objects;
import java.util.function.Supplier;

public class AcceleratorDAXDemo04Testing implements Supplier<String> {
    private final AcceleratorDAXDemo04Interface testTableService;

    public AcceleratorDAXDemo04Testing(AcceleratorDAXDemo04Interface service) {
        this.testTableService = service;
    }

    @Override
    public String get() {
        long startTime = System.currentTimeMillis();

        AcceleratorDAXDemo04 table = new AcceleratorDAXDemo04();
        table.setHashKey("hash");
        table.setRange("range");

        testTableService.save(table);

        for (int i = 0; i < 1000; i++) {
            Objects.requireNonNull(testTableService.load("hash", "range"));
        }

        long endTime = System.currentTimeMillis();
        return "table is loaded in " + (endTime - startTime) + " milliseconds";
    }
}