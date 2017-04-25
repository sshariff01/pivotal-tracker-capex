package com.pivotaltracker.capex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Iteration extends ResourceSupport {

    private final int currentIterationNumber;
    private final String currentIterationStart;
    private final String currentIterationFinish;
    private final int totalFeatureCycleTime;

    @JsonCreator
    public Iteration(int currentIterationNumber, String currentIterationStart, String currentIterationFinish, int totalFeatureCycleTime) {
        this.currentIterationNumber = currentIterationNumber;
        this.currentIterationStart = currentIterationStart;
        this.currentIterationFinish = currentIterationFinish;
        this.totalFeatureCycleTime = totalFeatureCycleTime;
    }

    @JsonProperty("current_iteration_number")
    public int getCurrentIterationNumber() {
        return currentIterationNumber;
    }

    @JsonProperty("current_iteration_start")
    public String getCurrentIterationStart() {
        return currentIterationStart;
    }

    @JsonProperty("current_iteration_finish")
    public String getCurrentIterationFinish() {
        return currentIterationFinish;
    }

    @JsonProperty("feature_cycle_time")
    public int getTotalFeatureCycleTime() {
        return totalFeatureCycleTime;
    }

    @JsonProperty("feature_cycle_time_units")
    public String getTotalFeatureCycleTimeUnits() {
        return "minutes";
    }
}
