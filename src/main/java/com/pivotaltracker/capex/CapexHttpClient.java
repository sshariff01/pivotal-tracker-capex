package com.pivotaltracker.capex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@Component
public class CapexHttpClient {

    @Value("${pivotalTracker.apiToken}")
    private String apiToken;

    @Value("${pivotalTracker.projectId}")
    private String projectId;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    ObjectMapper objectMapper;

    public ProjectDetails getProjectDetails() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TrackerToken", apiToken);
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String>  responseEntity = restTemplate.exchange(
                "https://www.pivotaltracker.com/services/v5/projects/" + projectId,
                HttpMethod.GET, entity, String.class);

        ProjectDetails projectDetails = objectMapper.readValue(responseEntity.getBody(), ProjectDetails.class);

        return projectDetails;
    }
}
