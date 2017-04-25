package com.pivotaltracker.capex.util;

import com.pivotaltracker.capex.model.Iteration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IterationFactoryTest {

    @Autowired
    IterationFactory iterationFactory;

    @Test
    public void should_returnIteration_when_givenIterationNumber() {
        Iteration iteration = iterationFactory.createIteration(1, "a string", "another string", 100);
        assertThat(iteration.getCurrentIterationNumber()).isEqualTo(1);
        assertThat(iteration.getCurrentIterationStart()).isEqualTo("a string");
        assertThat(iteration.getCurrentIterationFinish()).isEqualTo("another string");
        assertThat(iteration.getTotalFeatureCycleTime()).isEqualTo(100);
        assertThat(iteration.getTotalFeatureCycleTimeUnits()).isEqualTo("minutes");
    }

}
