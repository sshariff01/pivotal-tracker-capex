package com.pivotaltracker.capex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class CapexController {

    @Autowired
    private CapexLinkBuilder capexLinkBuilder;

    @RequestMapping("/")
    public ResponseEntity<Iteration> iteration() {
        Iteration iteration = new Iteration(1);
        iteration.add(capexLinkBuilder.buildLink());

        return new ResponseEntity<>(iteration, HttpStatus.OK);
    }
}
