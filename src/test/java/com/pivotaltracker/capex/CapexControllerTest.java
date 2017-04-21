package com.pivotaltracker.capex;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CapexControllerTest {

    @Mock
    CapexLinkBuilder capexLinkBuilder;

    @Mock
    CapexHttpClient capexHttpClient;

    @InjectMocks
    CapexController capexController;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws IOException {
        Link mockLink = mock(Link.class);
        when(capexLinkBuilder.buildLink()).thenReturn(mockLink);

        ProjectDetails projectDetails = objectMapper.readValue("{ \"current_iteration_number\": 1 }", ProjectDetails.class);
        when(capexHttpClient.getProjectDetails()).thenReturn(projectDetails);
    }

    @Test
    public void should_return200OkWithCurrentIterationNumber_when_invokedToRetrieveIteration() throws IOException {
        ResponseEntity<Iteration> responseEntity = capexController.iteration();

        verify(capexLinkBuilder).buildLink();
        verify(capexHttpClient).getProjectDetails();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(Iteration.class);
        assertThat(responseEntity.getBody().getCurrentIterationNumber()).isEqualTo(1);
    }

}
