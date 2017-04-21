package com.pivotaltracker.capex.controller;

import com.pivotaltracker.capex.model.Iteration;
import com.pivotaltracker.capex.util.CapexLinkBuilder;
import com.pivotaltracker.capex.util.IterationFactory;
import com.pivotaltracker.capex.util.ProjectDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @RequestMapping("/")
    public ResponseEntity<Iteration> iteration() throws IOException {
        Iteration iteration = iterationFactory.createIteration(projectDetailsRepository.getProjectDetails().getCurrentIterationNumber());
        iteration.add(capexLinkBuilder.buildLink());

        return new ResponseEntity<>(iteration, HttpStatus.OK);
    }
}
