package com.pivotaltracker.capex.controller;

import com.pivotaltracker.capex.model.Iteration;
import com.pivotaltracker.capex.model.ProjectDetails;
import com.pivotaltracker.capex.util.CapexLinkBuilder;
import com.pivotaltracker.capex.util.IterationFactory;
import com.pivotaltracker.capex.util.ProjectDetailsRepository;
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

    @InjectMocks
    CapexController capexController;

    @Before
    public void setUp() throws IOException {
        Link mockLink = mock(Link.class);
        when(capexLinkBuilder.buildLink()).thenReturn(mockLink);

        when(projectDetailsRepository.getProjectDetails()).thenReturn(new ProjectDetails(1));
    }

    @Test
    public void should_return200OkWithCurrentIterationNumber_when_invokedToRetrieveIteration() throws IOException {
        ResponseEntity<Iteration> responseEntity = capexController.iteration();

        verify(capexLinkBuilder).buildLink();
        verify(projectDetailsRepository).getProjectDetails();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(Iteration.class);
        assertThat(responseEntity.getBody().getCurrentIterationNumber()).isEqualTo(1);
    }

}
