package com.tenpo.calculator.controller;

import com.tenpo.calculator.dto.ResultDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CalculatorControllerTest {
    @Autowired
    CalculatorController calculatorController;

    @LocalServerPort
    private int serverPort;

    @Test
    void getServiceUnavailable() {

        PageImpl<ResultDTO> pageResultDTO;
        ArrayList<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
        resultDTOList.add(new ResultDTO(100.5d));
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.unsorted());
        pageResultDTO = new PageImpl<ResultDTO>(resultDTOList, pageRequest, resultDTOList.size());

        try {
            calculatorController.getValue(10.5d, 10.1d, pageRequest);
        } catch (HttpClientErrorException httpClientErrorException) {
            Assertions.assertEquals(httpClientErrorException.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @Test
    void badRequestInvalidParameter() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;
            response = restTemplate.getForEntity(("http://localhost:" + serverPort + "/api/v1/calculator/getValue?operand1=ZZ1&operand2=12.5"), String.class);
        } catch (HttpClientErrorException httpServerErrorException) {
            Assertions.assertEquals(httpServerErrorException.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    @Test
    void badRequestMissingParameter() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;
            response = restTemplate.getForEntity(("http://localhost:" + serverPort + "/api/v1/calculator/getValue?operand2=12.5"), String.class);
        } catch (HttpClientErrorException httpServerErrorException) {
            Assertions.assertEquals(httpServerErrorException.getStatusCode(), HttpStatus.BAD_REQUEST);
        }
    }

    private void askOneRequestIgnoringErros(){
        try {
            Thread.sleep(1);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;
            response = restTemplate.getForEntity(("http://localhost:" + serverPort + "/api/v1/calculator/getValue?operand1=1&operand2=12.5"), String.class);
        } catch (Exception e) {
        }
        return;
    }
    @Test
    void maxAttempts() {

        for (int i= 1;i<3+1;i++) {
            this.askOneRequestIgnoringErros();
        }
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response;
            response = restTemplate.getForEntity(("http://localhost:" + serverPort + "/api/v1/calculator/getValue?operand1=1&operand2=12.5"), String.class);
        } catch (HttpClientErrorException httpServerErrorException) {
            Assertions.assertEquals(httpServerErrorException.getStatusCode(), HttpStatus.TOO_MANY_REQUESTS);
        }

    }
}




