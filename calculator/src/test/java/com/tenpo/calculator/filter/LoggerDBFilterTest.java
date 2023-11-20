package com.tenpo.calculator.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoggerDBFilterTest {

    @LocalServerPort
    private int serverPort;

    @Test
    void filter() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;
            response = restTemplate.getForEntity(("http://localhost:" + serverPort + "/api/v1/calculator/getValue?operand1=11&operand2=12.5"), String.class);
        } catch (HttpServerErrorException httpServerErrorException) {
            Assertions.assertEquals(httpServerErrorException.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);

        }
    }
}
