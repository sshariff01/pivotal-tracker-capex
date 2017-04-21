package com.pivotaltracker.capex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CapexLinkBuilderTest {

    @Autowired
    CapexLinkBuilder capexLinkBuilder;

    @Test
    public void should_buildLinkForIteration() {
        assertThat(capexLinkBuilder.buildLink().getRel()).isEqualTo("self");
        assertThat(capexLinkBuilder.buildLink().getHref()).isEqualTo("http://localhost/");
    }

}
