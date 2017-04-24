package com.pivotaltracker.capex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IterationDetails {

    private String currentIterationStart;
    private String currentIterationFinish;

    @JsonCreator
    public IterationDetails(@JsonProperty("start") String currentIterationStart, @JsonProperty("finish") String currentIterationFinish) {
        this.currentIterationStart = currentIterationStart.substring(0, 10);
        this.currentIterationFinish = currentIterationFinish.substring(0, 10);
    }

    public String getCurrentIterationStart() {
        return this.currentIterationStart;
    }

    public String getCurrentIterationFinish() {
        return this.currentIterationFinish;
    }
}
