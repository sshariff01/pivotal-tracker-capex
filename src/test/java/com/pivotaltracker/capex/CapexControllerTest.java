package com.pivotaltracker.capex;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CapexControllerTest {

    @Mock
    CapexLinkBuilder capexLinkBuilder;

    @InjectMocks
    CapexController capexController;

    @Test
    public void should_return200OkWithCurrentIterationNumber_when_invokedToRetrieveIteration() {
        Link mockLink = mock(Link.class);
        when(capexLinkBuilder.buildLink()).thenReturn(mockLink);

        ResponseEntity<Iteration> responseEntity = capexController.iteration();

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(Iteration.class);
        assertThat(responseEntity.getBody().getCurrentIterationNumber()).isEqualTo(1);
    }

}
