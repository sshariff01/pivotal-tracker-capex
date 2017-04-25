package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.model.IterationDetails;
import com.pivotaltracker.capex.model.ProjectDetails;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProjectDetailsRepositoryTest {

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CapexHttpClient capexHttpClient;

    @InjectMocks
    private ProjectDetailsRepository projectDetailsRepository;

    @Before
    public void setUp() throws JSONException {
        ResponseEntity<String> mockProjectDetailsResponse = new ResponseEntity<>("{\"current_iteration_number\": 1000}", HttpStatus.OK);
        when(capexHttpClient.getProjectDetails()).thenReturn(mockProjectDetailsResponse);

        ResponseEntity<String> mockIterationDetailsResponse = new ResponseEntity<>("{ \"stories\": [ { \"id\": 558, \"story_type\": \"feature\", \"current_state\": \"accepted\" } ] }", HttpStatus.OK);
        when(capexHttpClient.getIterationDetails(any(Integer.class))).thenReturn(mockIterationDetailsResponse);

        ResponseEntity<String> mockCycleTimeResponse = new ResponseEntity<>("[ { \"total_cycle_time\": 363231000, \"story_id\": 558 } ]", HttpStatus.OK);
        when(capexHttpClient.getCycleTime(any(Integer.class))).thenReturn(mockCycleTimeResponse);
    }

    @Test
    public void should_returnCurrentIterationDetails() throws IOException {
        IterationDetails iterationDetails = projectDetailsRepository.getCurrentIterationDetails();

        verify(capexHttpClient).getProjectDetails();
        verify(capexHttpClient).getIterationDetails(1000);
        verify(capexHttpClient).getCycleTime(1000);
        assertThat(iterationDetails.getCurrentIterationNumber()).isEqualTo(1000);
    }

}
