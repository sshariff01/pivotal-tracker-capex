package com.pivotaltracker.capex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectDetails {

    private int currentIterationNumber;

    @JsonCreator
    public ProjectDetails(@JsonProperty("current_iteration_number") int currentIterationNumber) {
        this.currentIterationNumber = currentIterationNumber;
    }

    public int getCurrentIterationNumber() {
        return this.currentIterationNumber;
    }
}
