package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.http.response.IterationDetails;
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
public class IterationDetailsRepositoryTest {

    @Spy
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private CapexHttpClient capexHttpClient;

    @InjectMocks
    private IterationDetailsRepository iterationDetailsRepository;

    @Before
    public void setUp() throws JSONException {
        ResponseEntity<String> mockIterationDetailsResponse = new ResponseEntity<>("{\"start\": \"2017-04-24T07:00:00\",\"finish\": \"2017-05-01T07:00:00Z\"}", HttpStatus.OK);

        when(capexHttpClient.getIterationDetails(1)).thenReturn(mockIterationDetailsResponse);
    }

    @Test
    public void should_returnIterationDetails() throws IOException {
        IterationDetails iterationDetails = iterationDetailsRepository.getIterationDetails(1);

        verify(capexHttpClient).getIterationDetails(1);
        assertThat(iterationDetails.getCurrentIterationStart()).isInstanceOf(String.class);
        assertThat(iterationDetails.getCurrentIterationStart().length()).isEqualTo(10);
        assertThat(iterationDetails.getCurrentIterationFinish()).isInstanceOf(String.class);
        assertThat(iterationDetails.getCurrentIterationFinish().length()).isEqualTo(10);
    }

}
