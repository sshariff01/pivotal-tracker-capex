package com.pivotaltracker.capex.util;

import com.pivotaltracker.capex.model.Iteration;
import org.springframework.stereotype.Component;

@Component
public class IterationFactory {

    public Iteration createIteration(int iterationNumber) {
        return new Iteration(iterationNumber);
    }
}
