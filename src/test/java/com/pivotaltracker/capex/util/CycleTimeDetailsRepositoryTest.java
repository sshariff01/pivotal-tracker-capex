package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.http.response.Story;
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
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CycleTimeDetailsRepositoryTest {

    @Mock
    CapexHttpClient capexHttpClient;

    @Spy
    ObjectMapper objectMapper = new ObjectMapper();

    @InjectMocks
    CycleTimeDetailsRepository cycleTimeDetailsRepository;

    @Before
    public void setUp() {
        String mockResponseBody = "[ " +
                "{\"total_cycle_time\": 60000, \"story_id\": 10}, " +
                "{\"total_cycle_time\": 60000, \"story_id\": 20}, " +
                "{\"total_cycle_time\": 60000, \"story_id\": 30} " +
                "]";
        ResponseEntity<String> mockCycleTimeDetailsResponse = new ResponseEntity<>(mockResponseBody, HttpStatus.OK);
        when(capexHttpClient.getCycleTimeDetails(1)).thenReturn(mockCycleTimeDetailsResponse);
    }

    @Test
    public void should_returnZero_when_invokedGetTotalIterationFeatureCycleTime_given_noStories() throws IOException {
        int totalIterationFeatureCycleTime = cycleTimeDetailsRepository.getTotalIterationFeatureCycleTime(1, new ArrayList<>());

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationFeatureCycleTime).isEqualTo(0);
    }

    @Test
    public void should_returnTotalIterationFeatureCycleTime_given_oneFeature() throws IOException {
        Story story = new Story(10,"feature", "accepted");

        int totalIterationFeatureCycleTime = cycleTimeDetailsRepository.getTotalIterationFeatureCycleTime(1, Arrays.asList(story));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationFeatureCycleTime).isEqualTo(1);
    }

    @Test
    public void should_returnTotalFeatureCycleTime_given_multipleFeatures() throws IOException {
        Story story1 = new Story(10,"feature", "accepted");
        Story story2 = new Story(20,"feature", "accepted");
        Story story3 = new Story(30,"feature", "accepted");

        int totalIterationFeatureCycleTime = cycleTimeDetailsRepository.getTotalIterationFeatureCycleTime(1, Arrays.asList(story1, story2, story3));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationFeatureCycleTime).isEqualTo(3);
    }

    @Test
    public void should_returnZero_given_noStoriesThatAreFeatures() throws IOException {
        Story story1 = new Story(10,"not a feature", "accepted");
        Story story2 = new Story(20,"something that is not a feature", "accepted");

        int totalIterationFeatureCycleTime = cycleTimeDetailsRepository.getTotalIterationFeatureCycleTime(1, Arrays.asList(story1, story2));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationFeatureCycleTime).isEqualTo(0);
    }

    @Test
    public void should_returnZero_given_noAcceptedFeatures() throws IOException {
        Story story1 = new Story(10,"feature", "bad");
        Story story2 = new Story(20,"feature", "done horribly");

        int totalIterationFeatureCycleTime = cycleTimeDetailsRepository.getTotalIterationFeatureCycleTime(1, Arrays.asList(story1, story2));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationFeatureCycleTime).isEqualTo(0);
    }

    @Test
    public void should_returnZero_when_invokedGetTotalIterationBugCycleTime_given_noStories() throws IOException {
        int totalIterationBugCycleTime = cycleTimeDetailsRepository.getTotalIterationBugCycleTime(1, new ArrayList<>());

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationBugCycleTime).isEqualTo(0);
    }

    @Test
    public void should_returnTotalIterationBugCycleTime_given_oneBug() throws IOException {
        Story story = new Story(10,"bug", "accepted");

        int totalIterationBugCycleTime = cycleTimeDetailsRepository.getTotalIterationBugCycleTime(1, Arrays.asList(story));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationBugCycleTime).isEqualTo(1);
    }

    @Test
    public void should_returnTotalBugCycleTime_given_multipleBugs() throws IOException {
        Story story1 = new Story(10,"bug", "accepted");
        Story story2 = new Story(20,"bug", "accepted");
        Story story3 = new Story(30,"bug", "accepted");

        int totalIterationBugCycleTime = cycleTimeDetailsRepository.getTotalIterationBugCycleTime(1, Arrays.asList(story1, story2, story3));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationBugCycleTime).isEqualTo(3);
    }

    @Test
    public void should_returnZero_given_noStoriesThatAreBugs() throws IOException {
        Story story1 = new Story(10,"not a bug", "accepted");
        Story story2 = new Story(20,"something that is not a bug", "accepted");

        int totalIterationBugCycleTime = cycleTimeDetailsRepository.getTotalIterationBugCycleTime(1, Arrays.asList(story1, story2));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationBugCycleTime).isEqualTo(0);
    }

    @Test
    public void should_returnZero_given_noAcceptedBugs() throws IOException {
        Story story1 = new Story(10,"bug", "bad");
        Story story2 = new Story(20,"bug", "done horribly");

        int totalIterationBugCycleTime = cycleTimeDetailsRepository.getTotalIterationBugCycleTime(1, Arrays.asList(story1, story2));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(totalIterationBugCycleTime).isEqualTo(0);
    }
}