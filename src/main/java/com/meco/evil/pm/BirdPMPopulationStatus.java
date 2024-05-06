package com.meco.evil.pm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BirdPMPopulationStatus {
    LC("Least concern (LC)"),
    NT("Near Threatened (NT)"),
    VU("Vulnerable (VU)");
    String value;

    BirdPMPopulationStatus(String value) {
        this.value = value;
    }

    public static List<String> stringValues() {
        return Arrays.stream(values()).map(e -> e.value).collect(Collectors.toList());
    }
}
