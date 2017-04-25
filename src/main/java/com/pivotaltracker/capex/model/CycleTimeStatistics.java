package com.pivotaltracker.capex.model;


public class CycleTimeStatistics {
    private int totalFeatureCycleTime;
    private int totalBugCycleTime;

    public CycleTimeStatistics(int totalFeatureCycleTime, int totalBugCycleTime) {

        this.totalFeatureCycleTime = totalFeatureCycleTime;
        this.totalBugCycleTime = totalBugCycleTime;
    }

    public int getTotalFeatureCycleTime() {
        return totalFeatureCycleTime;
    }

    public int getTotalBugCycleTime() {
        return totalBugCycleTime;
    }
}
