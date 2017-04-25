package com.pivotaltracker.capex.http;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CapexHttpClientTest {

    @Autowired
    CapexHttpClient capexHttpClient;

    @Test
    public void should_returnProjectDetails() throws JSONException {
        ResponseEntity responseEntity = capexHttpClient.getProjectDetails();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONObject responseBody = new JSONObject(responseEntity.getBody().toString());
        assertThat(responseBody.has("current_iteration_number")).isTrue();
    }

    @Test
    public void should_returnIterationDetails() throws JSONException {
        Pattern dateRegex = Pattern.compile("\\d{4}-\\d{2}-\\d{2}.*");
        ResponseEntity responseEntity = capexHttpClient.getIterationDetails(1);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        JSONObject responseBody = new JSONObject(responseEntity.getBody().toString());
        assertThat(responseBody.has("start")).isTrue();
        assertThat(responseBody.has("finish")).isTrue();

        assertThat(responseBody.getString("start")).matches(dateRegex);
        assertThat(responseBody.getString("finish")).matches(dateRegex);
    }

}
