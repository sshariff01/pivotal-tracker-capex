package com.pivotaltracker.capex.model;

public class Feature {

    private int cycleTime;
    private String cycleTimeUnits = "minutes";

    public Feature(int cycleTime) {
        this.cycleTime = cycleTime;
    }

    public int getCycleTime() {
        return cycleTime;
    }

    public String getCycleTimeUnits() {
        return cycleTimeUnits;
    }
}
