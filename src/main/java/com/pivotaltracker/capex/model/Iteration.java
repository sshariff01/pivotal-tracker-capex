package com.pivotaltracker.capex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

public class Iteration extends ResourceSupport {

    private final int currentIterationNumber;

    @JsonCreator
    public Iteration(int currentIterationNumber) {
        this.currentIterationNumber = currentIterationNumber;
    }

    @JsonProperty("current_iteration_number")
    public int getCurrentIterationNumber() {
        return currentIterationNumber;
    }
}
