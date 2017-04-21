package com.pivotaltracker.capex;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
