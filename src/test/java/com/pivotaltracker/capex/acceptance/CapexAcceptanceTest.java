package com.pivotaltracker.capex.acceptance;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CapexAcceptanceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_respondWith200Ok_with_currentIterationNumber_when_GET_baseUrl() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/");
        MvcResult response = mockMvc.perform(requestBuilder)
            .andExpect(status().isOk())
            .andReturn();

        JSONObject responseBody = new JSONObject(response.getResponse().getContentAsString());
        assertThat(responseBody.get("current_iteration_number")).isInstanceOf(Integer.class);
    }

    @Test
    public void should_respondWith200Ok_with_currentIterationStartAndFinish_when_GET_baseUrl() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/");
        MvcResult response = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        JSONObject responseBody = new JSONObject(response.getResponse().getContentAsString());
        assertThat(responseBody.get("current_iteration_start")).isInstanceOf(String.class);
        assertThat(responseBody.get("current_iteration_finish")).isInstanceOf(String.class);
    }

    @Test
    public void should_respondWith200Ok_with_linkToHome_when_GET_baseUrl() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/");
        MvcResult response = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        JSONObject responseBody = new JSONObject(response.getResponse().getContentAsString());
        assertThat(responseBody.getJSONObject("_links").has("self")).isTrue();

        JSONObject self = responseBody.getJSONObject("_links").getJSONObject("self");
        Link link = new Link(self.getString("href"), "self");
        assertThat(link.getHref()).endsWith("/");
    }

    @Test
    public void should_respondWith200Ok_with_totalFeatureCycleTimeForCurrentIteration_when_GET_baseUrl() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = get("/");
        MvcResult response = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        JSONObject responseBody = new JSONObject(response.getResponse().getContentAsString());
        assertThat(responseBody.get("feature_cycle_time")).isInstanceOf(Integer.class);
        assertThat(responseBody.get("feature_cycle_time_units")).isEqualTo("minutes");
    }
}
