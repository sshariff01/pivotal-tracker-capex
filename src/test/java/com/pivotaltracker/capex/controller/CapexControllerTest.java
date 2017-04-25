package com.pivotaltracker.capex.controller;

import com.pivotaltracker.capex.http.response.IterationDetails;
import com.pivotaltracker.capex.http.response.ProjectDetails;
import com.pivotaltracker.capex.http.response.Story;
import com.pivotaltracker.capex.model.Iteration;
import com.pivotaltracker.capex.util.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CapexControllerTest {

    @Spy
    IterationFactory iterationFactory = new IterationFactory();

    @Mock
    CapexLinkBuilder capexLinkBuilder;

    @Mock
    ProjectDetailsRepository projectDetailsRepository;

    @Mock
    IterationDetailsRepository iterationDetailsRepository;

    @Mock
    CycleTimeDetailsRepository cycleTimeDetailsRepository;

    @InjectMocks
    CapexController capexController;

    List<Story> stories = Arrays.asList(mock(Story.class));

    @Before
    public void setUp() throws IOException {
        Link mockLink = mock(Link.class);
        when(capexLinkBuilder.buildLink()).thenReturn(mockLink);

        when(projectDetailsRepository.getProjectDetails()).thenReturn(new ProjectDetails(1));

        when(iterationDetailsRepository.getIterationDetails(1))
                .thenReturn(new IterationDetails(
                        "2017-04-24T07:00:00",
                        "2017-05-01T07:00:00",
                        stories));
        when(cycleTimeDetailsRepository.getTotalIterationFeatureCycleTime(1, stories)).thenReturn(850);
        when(cycleTimeDetailsRepository.getTotalIterationBugCycleTime(1, stories)).thenReturn(100);
    }

    @Test
    public void should_return200OkWithCurrentIterationNumber_when_invokedToRetrieveIteration() throws IOException {
        ResponseEntity<Iteration> responseEntity = capexController.iteration();

        verify(capexLinkBuilder).buildLink();
        verify(projectDetailsRepository).getProjectDetails();
        verify(iterationDetailsRepository).getIterationDetails(1);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(Iteration.class);
        assertThat(responseEntity.getBody().getCurrentIterationNumber()).isEqualTo(1);
        assertThat(responseEntity.getBody().getCurrentIterationStart()).isEqualTo("2017-04-24");
        assertThat(responseEntity.getBody().getCurrentIterationFinish()).isEqualTo("2017-05-01");
    }

    @Test
    public void should_return200OkWithTotalFeatureCycleTime_when_GET_baseUrl() throws IOException {
        ResponseEntity<Iteration> responseEntity = capexController.iteration();

        verify(capexLinkBuilder).buildLink();
        verify(projectDetailsRepository).getProjectDetails();
        verify(iterationDetailsRepository).getIterationDetails(1);
        verify(cycleTimeDetailsRepository).getTotalIterationFeatureCycleTime(1,stories);

        Iteration iteration = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(iteration).isInstanceOf(Iteration.class);
        assertThat(iteration.getTotalFeatureCycleTime()).isEqualTo(850);
        assertThat(iteration.getTotalFeatureCycleTimeUnits()).isEqualTo("minutes");
    }

    @Test
    public void should_return200OkWithTotalBugCycleTime_when_GET_baseUrl() throws IOException {
        ResponseEntity<Iteration> responseEntity = capexController.iteration();

        verify(capexLinkBuilder).buildLink();
        verify(projectDetailsRepository).getProjectDetails();
        verify(iterationDetailsRepository).getIterationDetails(1);
        verify(cycleTimeDetailsRepository).getTotalIterationBugCycleTime(1,stories);

        Iteration iteration = responseEntity.getBody();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(iteration).isInstanceOf(Iteration.class);
        assertThat(iteration.getTotalBugCycleTime()).isEqualTo(100);
        assertThat(iteration.getTotalBugCycleTimeUnits()).isEqualTo("minutes");
    }

}
