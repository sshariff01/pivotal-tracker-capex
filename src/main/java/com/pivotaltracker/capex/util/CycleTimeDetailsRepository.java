package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.http.response.CycleTimeDetails;
import com.pivotaltracker.capex.http.response.Story;
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

    public int getTotalIterationFeatureCycleTime(int currentIterationNumber, List<Story> stories) throws IOException {
        ResponseEntity<String> cycleTimeDetailsResponse = capexHttpClient.getCycleTimeDetails(currentIterationNumber);
        List<CycleTimeDetails> cycleTimeDetails = Arrays.asList(objectMapper.readValue(cycleTimeDetailsResponse.getBody(), CycleTimeDetails[].class));

        List<Story> acceptedFeatures = stories.stream()
                .filter(story -> story.getStoryType().equals("feature") && story.getStoryState().equals("accepted"))
                .collect(Collectors.toList());

        Map<Integer, Story> idToStory = new HashMap<>();
        for (Story story : acceptedFeatures) {
            idToStory.put(story.getStoryId(), story);
        }
        int totalFeaturesCycleTime = 0;
        for (CycleTimeDetails cycleTimeDetail : cycleTimeDetails) {
            int storyId = cycleTimeDetail.getStoryId();
            if (idToStory.containsKey(storyId)) {
                totalFeaturesCycleTime += cycleTimeDetail.getTotalCycleTime();
            }
        }

        return (totalFeaturesCycleTime / 1000) / 60;
    }

    public int getTotalIterationBugCycleTime(int currentIterationNumber, List<Story> stories) throws IOException{
        ResponseEntity<String> cycleTimeDetailsResponse = capexHttpClient.getCycleTimeDetails(currentIterationNumber);
        List<CycleTimeDetails> cycleTimeDetails = Arrays.asList(objectMapper.readValue(cycleTimeDetailsResponse.getBody(), CycleTimeDetails[].class));

        List<Story> acceptedBugs = stories.stream()
                .filter(story -> story.getStoryType().equals("bug") && story.getStoryState().equals("accepted"))
                .collect(Collectors.toList());

        Map<Integer, Story> idToStory = new HashMap<>();
        for (Story story : acceptedBugs) {
            idToStory.put(story.getStoryId(), story);
        }
        int totalBugsCycleTime = 0;
        for (CycleTimeDetails cycleTimeDetail : cycleTimeDetails) {
            int storyId = cycleTimeDetail.getStoryId();
            if (idToStory.containsKey(storyId)) {
                totalBugsCycleTime += cycleTimeDetail.getTotalCycleTime();
            }
        }

        return (totalBugsCycleTime / 1000) / 60;
    }
}
