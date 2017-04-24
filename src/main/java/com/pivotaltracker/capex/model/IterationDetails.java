package com.pivotaltracker.capex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class IterationDetails extends ResourceSupport {

    private final int currentIterationNumber;
    private List<Feature> features;

    @JsonCreator
    public IterationDetails(int currentIterationNumber, List<Feature> features) {
        this.currentIterationNumber = currentIterationNumber;
        this.features = features;
    }

    @JsonProperty("current_iteration_number")
    public int getCurrentIterationNumber() {
        return currentIterationNumber;
    }

    public List<Feature> getFeatures() {
        return features;
    }
}
