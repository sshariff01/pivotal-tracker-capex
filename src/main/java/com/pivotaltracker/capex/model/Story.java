package com.pivotaltracker.capex.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

    private int id;
    private String type;
    private String state;
    private int totalCycleTime;

    @JsonCreator
    public Story(@JsonProperty("id") int id,
                 @JsonProperty("story_type") String type,
                 @JsonProperty("current_state") String state) {
        this.id = id;
        this.type = type;
        this.state = state;
    }

    public Story(int totalCycleTime) {
        this.totalCycleTime = totalCycleTime;
    }

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getState() {
        return state;
    }

    public int getTotalCycleTime() {
        return totalCycleTime;
    }

    public void setTotalCycleTime(int totalCycleTime) {
        this.totalCycleTime = totalCycleTime;
    }
}
