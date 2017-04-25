package com.pivotaltracker.capex.http.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IterationDetails {

    private String currentIterationStart;
    private String currentIterationFinish;
    private List<Story> stories;

    @JsonCreator
    public IterationDetails(@JsonProperty("start") String currentIterationStart,
                            @JsonProperty("finish") String currentIterationFinish,
                            @JsonProperty("stories") List<Story> stories) {

        this.currentIterationStart = currentIterationStart.substring(0, 10);
        this.currentIterationFinish = currentIterationFinish.substring(0, 10);
        this.stories = stories;
    }

    public String getCurrentIterationStart() {
        return this.currentIterationStart;
    }

    public String getCurrentIterationFinish() {
        return this.currentIterationFinish;
    }

    public List<Story> getCurrentIterationStories() {
        return this.stories;
    }

}
