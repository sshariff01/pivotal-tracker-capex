package com.pivotaltracker.capex.model;


public class CycleTimeStatistics {
    private int totalFeatureCycleTime;
    private int totalBugCycleTime;
    private int totalChoreCycleTime;

    public CycleTimeStatistics(int totalFeatureCycleTime, int totalBugCycleTime, int totalChoreCycleTime) {

        this.totalFeatureCycleTime = totalFeatureCycleTime;
        this.totalBugCycleTime = totalBugCycleTime;
        this.totalChoreCycleTime = totalChoreCycleTime;
    }

    public int getTotalFeatureCycleTime() {
        return totalFeatureCycleTime;
    }

    public int getTotalBugCycleTime() {
        return totalBugCycleTime;
    }

    public int getTotalChoreCycleTime() { return totalChoreCycleTime; }
}
