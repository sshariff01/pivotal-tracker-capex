package com.pivotaltracker.capex.util;

import com.pivotaltracker.capex.model.Iteration;
import org.springframework.stereotype.Component;

@Component
public class IterationFactory {

    public Iteration createIteration(int iterationNumber, String startDate, String finishDate, int totalFeatureCycleTime, int totalBugCycleTime) {
        return new Iteration(iterationNumber, startDate, finishDate, totalFeatureCycleTime, totalBugCycleTime);
    }
}
