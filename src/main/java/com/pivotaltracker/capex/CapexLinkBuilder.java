package com.pivotaltracker.capex;

import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class CapexLinkBuilder {

    public Link buildLink() {
        return linkTo(methodOn(CapexController.class).iteration()).withSelfRel();
    }
}
