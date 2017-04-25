package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.http.response.CycleTimeDetails;
import com.pivotaltracker.capex.http.response.Story;
import com.pivotaltracker.capex.model.CycleTimeStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CycleTimeDetailsRepository {

    @Autowired
    private CapexHttpClient capexHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public CycleTimeStatistics getCycleTimeStatistics(int currentIterationNumber, List<Story> stories) throws IOException {
        ResponseEntity<String> cycleTimeDetailsResponse = capexHttpClient.getCycleTimeDetails(currentIterationNumber);
        List<CycleTimeDetails> cycleTimeDetails = Arrays.asList(objectMapper.readValue(cycleTimeDetailsResponse.getBody(), CycleTimeDetails[].class));
        int totalBugsCycleTimeInMinutes = getTotalCycleTimeInMinutes(cycleTimeDetails, stories, "bug");
        int totalFeaturesCycleTimeInMinutes = getTotalCycleTimeInMinutes(cycleTimeDetails, stories, "feature");

        return new CycleTimeStatistics(totalFeaturesCycleTimeInMinutes, totalBugsCycleTimeInMinutes);
    }

    private int getTotalCycleTimeInMinutes(List<CycleTimeDetails> cycleTimeDetails, List<Story> stories, String storyType) {
        List<Story> acceptedStories = stories.stream()
                .filter(story -> story.getStoryState().equals("accepted") && story.getStoryType().equals(storyType))
                .collect(Collectors.toList());

        Map<Integer, Story> idToStory = new HashMap<>();
        for (Story story : acceptedStories) {
            idToStory.put(story.getStoryId(), story);
        }
        int totalCycleTime = 0;
        for (CycleTimeDetails cycleTimeDetail : cycleTimeDetails) {
            int storyId = cycleTimeDetail.getStoryId();
            if (idToStory.containsKey(storyId)) {
                totalCycleTime += cycleTimeDetail.getTotalCycleTime();
            }
        }

        return convertToMinutes(totalCycleTime);
    }

    private int convertToMinutes(int totalCycleTime) {
        return totalCycleTime / 60000;
    }

}
