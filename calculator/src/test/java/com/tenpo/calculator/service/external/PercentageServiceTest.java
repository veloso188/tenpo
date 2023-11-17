package com.tenpo.calculator.service.external;


import com.tenpo.calculator.service.LoggerDBServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PercentageServiceTest {

    @MockBean
    PercentageServiceImpl percentageServiceMock;

    @Mock
    LoggerDBServiceImpl loggerDBServiceMock;

    @Mock
    RestTemplate restTemplateMock;

    @Value("${percentage.service.url}")
    private String percentageServiceURL;

    @Test
    void setPercentageServiceMock() {
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

        when(restTemplateMock.getForEntity(null, String.class)).thenReturn(response);
        when(percentageServiceMock.getRestTemplate()).thenReturn(restTemplateMock);
        doNothing().when(loggerDBServiceMock).saveRequestResponseLog(anyString(),anyString(),any(LocalDateTime.class),anyString(),anyString(),anyString());
        percentageServiceMock.getPercentageFromExternalService();
    }
}
