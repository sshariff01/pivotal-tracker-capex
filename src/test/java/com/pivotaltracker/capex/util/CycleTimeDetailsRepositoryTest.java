package com.pivotaltracker.capex.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pivotaltracker.capex.http.CapexHttpClient;
import com.pivotaltracker.capex.http.response.Story;
import com.pivotaltracker.capex.model.CycleTimeStatistics;
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
    public void should_returnCycleTimeStatistics_with_zeroFeatureAndBugCycleTimes_given_noStories() throws IOException {
        CycleTimeStatistics cycleTimeStatistics = cycleTimeDetailsRepository.getCycleTimeStatistics(1, new ArrayList<Story>());

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(cycleTimeStatistics.getTotalFeatureCycleTime()).isEqualTo(0);
        assertThat(cycleTimeStatistics.getTotalBugCycleTime()).isEqualTo(0);
    }

    @Test
    public void should_returnCycleTimeStatistics_with_zeroFeatureCycleTimeAndNonZeroBugCycleTime_given_onlyBugsInAcceptedState() throws IOException {
        Story story = new Story(10,"bug", "accepted");
        CycleTimeStatistics cycleTimeStatistics = cycleTimeDetailsRepository.getCycleTimeStatistics(1, Arrays.asList(story));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(cycleTimeStatistics.getTotalFeatureCycleTime()).isEqualTo(0);
        assertThat(cycleTimeStatistics.getTotalBugCycleTime()).isEqualTo(1);
    }

    @Test
    public void should_returnCycleTimeStatistics_with_zeroFeatureCycleTimeAndNonZeroBugCycleTime_given_onlyBugsInDifferentStates() throws IOException {
        Story story1 = new Story(10,"bug", "accepted");
        Story story2 = new Story(20,"bug", "open");
        Story story3 = new Story(30,"bug", "wrong");
        CycleTimeStatistics cycleTimeStatistics = cycleTimeDetailsRepository.getCycleTimeStatistics(1, Arrays.asList(story1, story2, story3));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(cycleTimeStatistics.getTotalFeatureCycleTime()).isEqualTo(0);
        assertThat(cycleTimeStatistics.getTotalBugCycleTime()).isEqualTo(1);
    }

    @Test
    public void should_returnCycleTimeStatistics_with_zeroFeatureCycleTimeAndNonZeroBugCycleTime_given_onlyFeaturesInAcceptedState() throws IOException {
        Story story = new Story(10,"feature", "accepted");
        CycleTimeStatistics cycleTimeStatistics = cycleTimeDetailsRepository.getCycleTimeStatistics(1, Arrays.asList(story));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(cycleTimeStatistics.getTotalFeatureCycleTime()).isEqualTo(1);
        assertThat(cycleTimeStatistics.getTotalBugCycleTime()).isEqualTo(0);
    }

    @Test
    public void should_returnCycleTimeStatistics_with_zeroFeatureCycleTimeAndNonZeroBugCycleTime_given_onlyFeaturesInDifferentStates() throws IOException {
        Story story1 = new Story(10,"feature", "accepted");
        Story story2 = new Story(20,"feature", "open");
        Story story3 = new Story(30,"feature", "wrong");

        CycleTimeStatistics cycleTimeStatistics = cycleTimeDetailsRepository.getCycleTimeStatistics(1, Arrays.asList(story1, story2, story3));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(cycleTimeStatistics.getTotalFeatureCycleTime()).isEqualTo(1);
        assertThat(cycleTimeStatistics.getTotalBugCycleTime()).isEqualTo(0);
    }

    @Test
    public void should_returnCycleTimeStatistics_with_nonZeroFeatureAndBugCycleTimes_given_bothFeaturesAndBugsThatAreAccepted() throws IOException {
        Story story1 = new Story(10,"feature", "accepted");
        Story story2 = new Story(20,"bug", "accepted");

        CycleTimeStatistics cycleTimeStatistics = cycleTimeDetailsRepository.getCycleTimeStatistics(1, Arrays.asList(story1, story2));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(cycleTimeStatistics.getTotalFeatureCycleTime()).isEqualTo(1);
        assertThat(cycleTimeStatistics.getTotalBugCycleTime()).isEqualTo(1);
    }

    @Test
    public void should_returnCycleTimeStatistics_with_nonZeroFeatureAndBugCycleTimes_given_bothFeaturesAndBugsInDifferentStates() throws IOException {
        Story story1 = new Story(10,"feature", "accepted");
        Story story2 = new Story(20,"feature", "lol");
        Story story3 = new Story(30,"bug", "accepted");
        Story story4 = new Story(40,"bug", "yolo");

        CycleTimeStatistics cycleTimeStatistics = cycleTimeDetailsRepository.getCycleTimeStatistics(1, Arrays.asList(story1, story2, story3, story4));

        verify(capexHttpClient).getCycleTimeDetails(1);
        assertThat(cycleTimeStatistics.getTotalFeatureCycleTime()).isEqualTo(1);
        assertThat(cycleTimeStatistics.getTotalBugCycleTime()).isEqualTo(1);
    }

}