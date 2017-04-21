package com.pivotaltracker.capex.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CapexTraversonTest {

    @LocalServerPort
    private int port;

    @Test
    public void envEndpointNotHidden() throws Exception {
        Traverson traverson = new Traverson(new URI("http://localhost:" + this.port + "/"), MediaTypes.HAL_JSON);

        int iteration = traverson.follow("self").toObject("$.current_iteration_number");

        assertThat(iteration).isEqualTo(1);
    }
}
