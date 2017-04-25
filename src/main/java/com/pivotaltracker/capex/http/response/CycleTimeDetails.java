package com.pivotaltracker.capex.http.response;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CycleTimeDetails {

    private int totalCycleTime;

    private int storyId;

    @JsonCreator
    public CycleTimeDetails(@JsonProperty("total_cycle_time") int totalCycleTime, @JsonProperty("story_id") int storyId) {
        this.totalCycleTime = totalCycleTime;
        this.storyId = storyId;
    }

    public int getTotalCycleTime() {
        return totalCycleTime;
    }

    public int getStoryId() {
        return storyId;
    }

}
