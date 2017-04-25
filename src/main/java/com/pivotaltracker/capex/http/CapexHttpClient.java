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

    @Value("${pivotalTracker.baseUrl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<String> getProjectDetails() {
        HttpEntity<HttpHeaders> headers = getHttpHeadersHttpEntity();

        ResponseEntity<String>  responseEntity = restTemplate.exchange(
                baseUrl + projectId,
                HttpMethod.GET, headers, String.class);

        return responseEntity;
    }

    public ResponseEntity<String> getIterationDetails(int iterationNumber) {
        HttpEntity<HttpHeaders> headers = getHttpHeadersHttpEntity();

        ResponseEntity<String>  responseEntity = restTemplate.exchange(
                baseUrl + projectId + "/iterations/" + iterationNumber,
                HttpMethod.GET, headers, String.class);

        return responseEntity;
    }

    public ResponseEntity<String> getCycleTimeDetails(int iterationNumber) {
        HttpEntity<HttpHeaders> headers = getHttpHeadersHttpEntity();

        ResponseEntity<String>  responseEntity = restTemplate.exchange(
                baseUrl + projectId + "/iterations/" + iterationNumber + "/analytics/cycle_time_details",
                HttpMethod.GET, headers, String.class);

        return responseEntity;
    }

    private HttpEntity<HttpHeaders> getHttpHeadersHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-TrackerToken", apiToken);
        return new HttpEntity<>(null, headers);
    }
}
