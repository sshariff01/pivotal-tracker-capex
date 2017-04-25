package com.pivotaltracker.capex.http.response;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Story {

    private int storyId;
    private String storyType;
    private String storyState;

    @JsonCreator
    public Story(@JsonProperty("id") int storyId,
                 @JsonProperty("story_type") String storyType,
                 @JsonProperty("current_state") String storyState) {
        this.storyId = storyId;
        this.storyType = storyType;
        this.storyState = storyState;
    }

    public int getStoryId() {
        return storyId;
    }

    public String getStoryType() {
        return storyType;
    }

    public String getStoryState() {
        return storyState;
    }
}