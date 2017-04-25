package com.pivotaltracker.capex.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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

    public ResponseEntity<String> getIterationDetails(int iterationNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TrackerToken", apiToken);
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String>  responseEntity = restTemplate.exchange(
                "https://www.pivotaltracker.com/services/v5/projects/" + projectId + "/iterations/" + iterationNumber,
                HttpMethod.GET, entity, String.class);

        return responseEntity;
    }

    public ResponseEntity<String> getCycleTimeDetails(int iterationNumber) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TrackerToken", apiToken);
        HttpEntity<HttpHeaders> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String>  responseEntity = restTemplate.exchange(
                "https://www.pivotaltracker.com/services/v5/projects/" + projectId + "/iterations/" + iterationNumber + "/analytics/cycle_time_details",
                HttpMethod.GET, entity, String.class);

        return responseEntity;
    }
}
