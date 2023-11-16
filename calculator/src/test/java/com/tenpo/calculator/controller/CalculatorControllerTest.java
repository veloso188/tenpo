package com.tenpo.calculator.controller;

import com.tenpo.calculator.dto.ResultDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;


@SpringBootTest
public class CalculatorControllerTest {
    @Autowired
    CalculatorController calculatorController;

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

}




