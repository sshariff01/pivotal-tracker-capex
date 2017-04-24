package com.pivotaltracker.capex.util;

import com.pivotaltracker.capex.model.IterationDetails;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class IterationDetailsFactoryTest {

    @Autowired
    IterationFactory iterationFactory;

    @Test
    public void should_returnIteration_when_givenIterationNumber() {
        assertThat(iterationFactory.createIteration(1)).isInstanceOf(IterationDetails.class);
    }

}