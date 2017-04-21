package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.model.ProjectDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ProjectDetailsRepository {

    @Autowired
    private CapexHttpClient capexHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public ProjectDetails getProjectDetails() throws IOException {
        ResponseEntity<String> responseEntity = capexHttpClient.getProjectDetails();

        ProjectDetails projectDetails = objectMapper.readValue(responseEntity.getBody(), ProjectDetails.class);

        return projectDetails;
    }

}
