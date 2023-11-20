package com.tenpo.calculator.service;

import com.tenpo.calculator.dto.ResultDTO;
import com.tenpo.calculator.service.external.PercentageServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;

import static org.mockito.Mockito.when;

@SpringBootTest
public class CalculatorServiceTest {
    @Autowired CalculatorService calculatorService;

    @InjectMocks
    CalculatorServiceImpl calculatorServiceMock;

    @Mock
    PercentageServiceImpl PercentageServiceImplMock;


    @Test
    void basicTest() {
        PageImpl<ResultDTO> pageResultDTO;
        ArrayList<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.unsorted());

        try {
            calculatorService.getValue(10.5d, 10.1d, pageRequest);
        } catch (HttpClientErrorException httpClientErrorException) {
            Assertions.assertEquals(httpClientErrorException.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);

        }
    }

    @Test
    void mockResultCalculationTest() {
        PageImpl<ResultDTO> pageResultDTO;
        ArrayList<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.unsorted());
        pageResultDTO = new PageImpl<ResultDTO>(resultDTOList, pageRequest, 1);

        when(PercentageServiceImplMock.getPercentage()).thenReturn(12.5d);
        Page<ResultDTO> resultPage = calculatorServiceMock.getValue(10.5d, 20.5d, pageRequest);
        Assertions.assertEquals(resultPage.getContent().get(0).getResult(),34.875d);
    }
}
