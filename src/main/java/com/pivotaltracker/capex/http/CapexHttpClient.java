package com.pivotaltracker.capex.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.model.ProjectDetails;
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

    public ResponseEntity<String> getProjectDetails() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TrackerToken", apiToken);
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String>  responseEntity = restTemplate.exchange(
                "https://www.pivotaltracker.com/services/v5/projects/" + projectId,
                HttpMethod.GET, entity, String.class);

        return responseEntity;
    }
}
