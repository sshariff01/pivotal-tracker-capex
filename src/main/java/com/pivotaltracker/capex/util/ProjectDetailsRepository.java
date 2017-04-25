package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.model.IterationDetails;
import com.pivotaltracker.capex.model.ProjectDetails;

import com.pivotaltracker.capex.model.Story;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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
public class ProjectDetailsRepository {

    @Autowired
    private CapexHttpClient capexHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public IterationDetails getCurrentIterationDetails() throws IOException, JSONException {
        ProjectDetails projectDetails = getProjectDetails();
        List<Story> stories = getStories(projectDetails);
        ResponseEntity<String> cycleTimeResponse = capexHttpClient.getCycleTime(projectDetails.getCurrentIterationNumber());

        return getIterationDetails(projectDetails, stories, cycleTimeResponse);
    }

    private ProjectDetails getProjectDetails() throws IOException {
        ResponseEntity<String> projectDetailsResponse = capexHttpClient.getProjectDetails();
        return objectMapper.readValue(projectDetailsResponse.getBody(), ProjectDetails.class);
    }

    private List<Story> getStories(ProjectDetails projectDetails) throws IOException {
        ResponseEntity<String> iterationDetailsResponse = capexHttpClient.getIterationDetails(projectDetails.getCurrentIterationNumber());
        JSONArray storiesResponseArray = new JSONObject(iterationDetailsResponse.getBody()).getJSONArray("stories");
        return Arrays.asList(objectMapper.readValue(storiesResponseArray.toString(), Story[].class));
    }

    private IterationDetails getIterationDetails(ProjectDetails projectDetails, List<Story> stories, ResponseEntity<String> cycleTimeResponse) {
        JSONArray cycleTimesResponseArray = new JSONArray(cycleTimeResponse.getBody());

//        stories.stream()
//                .map(s -> s.getId())
//                .forEach(story -> {
//
//                });
//                .filter(story -> {
//                    for (int i = 0; i < cycleTimesResponseArray.length(); i++) {
//                        JSONObject cycleTime = cycleTimesResponseArray.getJSONObject(i);
//                        if ()
//                    }
//                })
//
//        cycleTimesResponseArray.toList().stream()
//            .map(jsonObject -> (JSONObject) jsonObject)
//            .filter((object) -> stories.contains(object.getInt("story_id")))
//            .map(jsonObject -> {  })
//            .collect(Collectors.toList());


        for (int i = 0; i < cycleTimesResponseArray.length(); i++) {
            JSONObject cycleTime = cycleTimesResponseArray.getJSONObject(i);

            for (Story story : stories) {
                if (story.getId() == cycleTime.getInt("story_id")) {
                    story.setTotalCycleTime(cycleTime.getInt("total_cycle_time"));
                }
            }

        }

        return new IterationDetails(projectDetails.getCurrentIterationNumber(), stories);
    }

}
