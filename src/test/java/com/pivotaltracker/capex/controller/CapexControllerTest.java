package com.pivotaltracker.capex.controller;

import com.pivotaltracker.capex.model.Feature;
import com.pivotaltracker.capex.model.IterationDetails;
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
import java.util.ArrayList;
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

    @InjectMocks
    CapexController capexController;

    @Before
    public void setUp() throws IOException {
        Link mockLink = mock(Link.class);
        when(capexLinkBuilder.buildLink()).thenReturn(mockLink);

        List<Feature> features = new ArrayList<>();
        features.add(new Feature(1000000000));
        when(projectDetailsRepository.getCurrentIterationDetails()).thenReturn(new IterationDetails(1, features));
    }

    @Test
    public void should_return200OkWithCurrentIterationNumber_when_invokedToRetrieveIterationDetails() throws IOException {
        ResponseEntity<IterationDetails> responseEntity = capexController.iteration();

        verify(capexLinkBuilder).buildLink();
        verify(projectDetailsRepository).getCurrentIterationDetails();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        IterationDetails iterationDetails = responseEntity.getBody();
        assertThat(iterationDetails).isInstanceOf(IterationDetails.class);
        assertThat(iterationDetails.getCurrentIterationNumber()).isEqualTo(1);
    }

    @Test
    public void should_return200OkWithFeatureCycleTime_when_invokedToRetrieveIterationDetails() throws IOException {
        ResponseEntity<IterationDetails> responseEntity = capexController.iteration();

        verify(capexLinkBuilder).buildLink();
        verify(projectDetailsRepository).getCurrentIterationDetails();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        IterationDetails iterationDetails = responseEntity.getBody();
        assertThat(iterationDetails).isInstanceOf(IterationDetails.class);

        List<Feature> stories = iterationDetails.getFeatures();
        assertThat(stories.get(0).getCycleTime()).isEqualTo(1000000000);
        assertThat(stories.get(0).getCycleTimeUnits()).isEqualTo("minutes");
    }

}
