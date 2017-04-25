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

        List<Story> acceptedStories = stories.stream()
                .filter(story ->
                        (story.getStoryState().equals("accepted")) &&
                                (story.getStoryType().equals("bug") || story.getStoryType().equals("feature")))
                .collect(Collectors.toList());

        Map<Integer, Story> idToStory = new HashMap<>();
        for (Story story : acceptedStories) {
            idToStory.put(story.getStoryId(), story);
        }
        int totalBugsCycleTime = 0;
        int totalFeaturesCycleTime = 0;
        for (CycleTimeDetails cycleTimeDetail : cycleTimeDetails) {
            int storyId = cycleTimeDetail.getStoryId();
            if (idToStory.containsKey(storyId)) {
                if (idToStory.get(storyId).getStoryType().equals("bug")) {
                    totalBugsCycleTime += cycleTimeDetail.getTotalCycleTime();
                } else {
                    totalFeaturesCycleTime += cycleTimeDetail.getTotalCycleTime();
                }
            }
        }

        int totalBugsCycleTimeInMinutes = (totalBugsCycleTime / 1000) / 60;
        int totalFeaturesCycleTimeInMinutes = (totalFeaturesCycleTime /1000) / 60;

        return new CycleTimeStatistics(totalFeaturesCycleTimeInMinutes, totalBugsCycleTimeInMinutes);
    }
}
