package com.meco.evil.pm;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum BirdPMPopulationTrend {
    NONE("-"),
    UNKNOWN("Unknown"),
    STABLE("Stable"),
    INCREASING("Increasing"),
    DECREASING("Decreasing");
    String value;

    BirdPMPopulationTrend(String value) {
        this.value = value;
    }

    public static List<String> stringValues() {
        return Arrays.stream(values()).map(e -> e.value).collect(Collectors.toList());
    }
}
