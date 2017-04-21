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

}
