package com.pivotaltracker.capex.controller;

import com.pivotaltracker.capex.model.Iteration;
import com.pivotaltracker.capex.http.response.IterationDetails;
import com.pivotaltracker.capex.http.response.ProjectDetails;
import com.pivotaltracker.capex.util.*;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CapexController {
    @Autowired
    private IterationFactory iterationFactory;

    @Autowired
    private CapexLinkBuilder capexLinkBuilder;

    @Autowired
    private ProjectDetailsRepository projectDetailsRepository;

    @Autowired
    private IterationDetailsRepository iterationDetailsRepository;

    @Autowired
    private CycleTimeDetailsRepository cycleTimeDetailsRepository;

    @ApiOperation(value = "/")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = Iteration.class)})
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ResponseEntity<Iteration> iteration() throws IOException {
        ProjectDetails projectDetails  = projectDetailsRepository.getProjectDetails();
        IterationDetails iterationDetails  = iterationDetailsRepository.getIterationDetails(projectDetails.getCurrentIterationNumber());
        int totalFeatureCycleTime = cycleTimeDetailsRepository.getTotalIterationFeatureCycleTime(projectDetails.getCurrentIterationNumber(), iterationDetails.getCurrentIterationStories());

        Iteration iteration = iterationFactory.createIteration(
                projectDetails.getCurrentIterationNumber(),
                iterationDetails.getCurrentIterationStart(),
                iterationDetails.getCurrentIterationFinish(),
                totalFeatureCycleTime);
        iteration.add(capexLinkBuilder.buildLink());

        return new ResponseEntity<>(iteration, HttpStatus.OK);
    }
}
