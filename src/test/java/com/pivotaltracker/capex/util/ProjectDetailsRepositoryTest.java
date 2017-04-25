package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.http.response.ProjectDetails;
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
    }

    @Test
    public void should_returnProjectDetails() throws IOException {
        ProjectDetails projectDetails = projectDetailsRepository.getProjectDetails();

        verify(capexHttpClient).getProjectDetails();
        assertThat(projectDetails.getCurrentIterationNumber()).isInstanceOf(Integer.class);
    }

}
