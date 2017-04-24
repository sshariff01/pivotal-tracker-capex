package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.model.IterationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class IterationDetailsRepository {

    @Autowired
    private CapexHttpClient capexHttpClient;

    @Autowired
    private ObjectMapper objectMapper;

    public IterationDetails getIterationDetails(int iterationNumber) throws IOException {
        ResponseEntity<String> responseEntity = capexHttpClient.getIterationDetails(iterationNumber);

        IterationDetails iterationDetails = objectMapper.readValue(responseEntity.getBody(), IterationDetails.class);

        return iterationDetails;
    }

}