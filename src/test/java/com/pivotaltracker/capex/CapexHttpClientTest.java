package com.pivotaltracker.capex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CapexHttpClientTest {

    @Autowired
    CapexHttpClient capexHttpClient;

    @Test
    public void should_returnProjectDetails() throws IOException {
        ProjectDetails projectDetails = capexHttpClient.getProjectDetails();

        assertThat(projectDetails).isNotNull();
        assertThat(projectDetails.getCurrentIterationNumber()).isInstanceOf(Integer.class);
        assertThat(projectDetails.getCurrentIterationNumber()).isGreaterThan(0);
    }

}
