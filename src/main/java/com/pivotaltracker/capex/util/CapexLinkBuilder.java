package com.pivotaltracker.capex.util;

import com.pivotaltracker.capex.controller.CapexController;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CapexLinkBuilder {

    public Link buildLink() throws IOException {
        return linkTo(methodOn(CapexController.class).iteration()).withSelfRel();
    }
}
