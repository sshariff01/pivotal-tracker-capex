package com.pivotaltracker.capex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class IterationDetails extends ResourceSupport {

    private int currentIterationNumber;
    private List<Story> stories;

    @JsonCreator
    public IterationDetails(int currentIterationNumber, List<Story> stories) {
        this.currentIterationNumber = currentIterationNumber;
        this.stories = stories;
    }

    @JsonProperty("current_iteration_number")
    public int getCurrentIterationNumber() {
        return currentIterationNumber;
    }

    public List<Story> getStories() {
        return stories;
    }
}
