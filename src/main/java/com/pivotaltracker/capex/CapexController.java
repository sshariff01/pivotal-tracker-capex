package com.pivotaltracker.capex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class CapexController {

    @Autowired
    private CapexLinkBuilder capexLinkBuilder;

    @Autowired
    private CapexHttpClient capexHttpClient;

    @RequestMapping("/")
    public ResponseEntity<Iteration> iteration() throws IOException {
        Iteration iteration = new Iteration(capexHttpClient.getProjectDetails().getCurrentIterationNumber());
        iteration.add(capexLinkBuilder.buildLink());

        return new ResponseEntity<>(iteration, HttpStatus.OK);
    }
}
