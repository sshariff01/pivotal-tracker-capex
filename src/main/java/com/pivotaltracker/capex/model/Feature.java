package com.pivotaltracker.capex.model;

public class Feature {

    private int totalCycleTime;

    private String cycleTimeUnits = "minutes";

    public Feature(int totalCycleTime) {
        this.totalCycleTime = totalCycleTime;
    }

    public int getTotalCycleTime() {
        return totalCycleTime;
    }

    public String getCycleTimeUnits() {
        return cycleTimeUnits;
    }

    public int getId() {
        return -1;
    }

    public void setTotalCycleTime(int totalCycleTime) {
        this.totalCycleTime = totalCycleTime;
    }
}
