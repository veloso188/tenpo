package com.tenpo.calculator.service.external;


import com.tenpo.calculator.service.LoggerDBServiceImpl;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PercentageServiceTest {

    @Autowired
    PercentageServiceImpl percentageServiceMock;

    @Mock
    LoggerDBServiceImpl loggerDBServiceMock;

    @Mock
    RestTemplate restTemplateMock;

    @Value("${percentage.service.url}")
    private String percentageServiceURL;
    @Value("${percentage.service.max_attempts}")
    private int max_attempts;


    @Test
    void getPercentageServiceMock() {
        ResponseEntity<String> response = new ResponseEntity<String> ("{\n" +
                "    \"content\": [\n" +
                "        {\n" +
                "            \"percentage\": 12.5\n" +
                "        }\n" +
                "    ],\n" +
                "    \"pageable\": {\n" +
                "        \"pageNumber\": 0,\n" +
                "        \"pageSize\": 20,\n" +
                "        \"sort\": {\n" +
                "            \"sorted\": false,\n" +
                "            \"unsorted\": true,\n" +
                "            \"empty\": true\n" +
                "        },\n" +
                "        \"offset\": 0,\n" +
                "        \"paged\": true,\n" +
                "        \"unpaged\": false\n" +
                "    },\n" +
                "    \"totalPages\": 1,\n" +
                "    \"totalElements\": 1,\n" +
                "    \"last\": true,\n" +
                "    \"size\": 20,\n" +
                "    \"number\": 0,\n" +
                "    \"sort\": {\n" +
                "        \"sorted\": false,\n" +
                "        \"unsorted\": true,\n" +
                "        \"empty\": true\n" +
                "    },\n" +
                "    \"numberOfElements\": 1,\n" +
                "    \"first\": true,\n" +
                "    \"empty\": false\n" +
                "}", HttpStatus.OK);

        percentageServiceMock.setRestTemplateForTest(restTemplateMock);
        when(restTemplateMock.getForEntity(percentageServiceURL, String.class)).thenReturn(response);
       //when(percentageServiceMock.getRestTemplate()).thenReturn(restTemplateMock);
     //  doNothing().when(loggerDBServiceMock).saveRequestResponseLog(anyString(),anyString(),any(LocalDateTime.class),anyString(),anyString(),anyString());

        percentageServiceMock.getPercentageFromExternalService();
//        try {percentageServiceMock.getPercentageFromExternalService();}
//        catch (Exception e){
//            System.out.println(e.getMessage());
//            System.out.println(ExceptionUtils.getStackTrace(e));


//        }
    }













    @Test
    void getPercentageServiceMockBadRequest() {
        ResponseEntity<String> response = new ResponseEntity<String> ("", HttpStatus.BAD_REQUEST);

        percentageServiceMock.setRestTemplateForTest(restTemplateMock);
        when(restTemplateMock.getForEntity(percentageServiceURL, String.class)).thenReturn(response);

        try {
            percentageServiceMock.getPercentageFromExternalService();
        }
        catch (HttpClientErrorException httpClientErrorException){
            Assertions.assertEquals(httpClientErrorException.getStatusCode(),HttpStatus.SERVICE_UNAVAILABLE);
            Assertions.assertEquals(httpClientErrorException.getMessage(),"503 Percentage service is down and no cached value to return");
        }
    }
    @Test
    void getPercentageServiceMockInvalidAwnser() {
        ResponseEntity<String> response = new ResponseEntity<String> ("", HttpStatus.OK);

        percentageServiceMock.setRestTemplateForTest(restTemplateMock);
        when(restTemplateMock.getForEntity(percentageServiceURL, String.class)).thenReturn(response);

        try {
            percentageServiceMock.getPercentageFromExternalService();
        }
        catch (HttpClientErrorException httpClientErrorException){
            Assertions.assertEquals(httpClientErrorException.getStatusCode(),HttpStatus.SERVICE_UNAVAILABLE);
            Assertions.assertEquals(httpClientErrorException.getMessage(),"503 Percentage service is down and no cached value to return");
        }
    }


    @Test
    void getPercentageServiceMockInvalidAwnserNull() {
        String nullStr = null;
        ResponseEntity<String> response = new ResponseEntity<String> (nullStr, HttpStatus.OK);

        percentageServiceMock.setRestTemplateForTest(restTemplateMock);
        when(restTemplateMock.getForEntity(percentageServiceURL, String.class)).thenReturn(response);

        try {
            percentageServiceMock.getPercentageFromExternalService();
        }
        catch (HttpClientErrorException httpClientErrorException){
            Assertions.assertEquals(httpClientErrorException.getStatusCode(),HttpStatus.SERVICE_UNAVAILABLE);
            Assertions.assertEquals(httpClientErrorException.getMessage(),"503 Percentage service is down and no cached value to return");
        }
        verify(restTemplateMock,times(max_attempts)).getForEntity(percentageServiceURL,String.class);
    }

}
