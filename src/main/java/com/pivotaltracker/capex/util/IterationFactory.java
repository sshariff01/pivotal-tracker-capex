package com.pivotaltracker.capex.util;

import com.pivotaltracker.capex.model.IterationDetails;
import org.springframework.stereotype.Component;

@Component
public class IterationFactory {

    public IterationDetails createIteration(int iterationNumber) {
        return new IterationDetails(iterationNumber, null);
    }
}
