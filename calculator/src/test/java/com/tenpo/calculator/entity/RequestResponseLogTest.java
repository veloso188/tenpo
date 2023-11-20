package com.tenpo.calculator.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;

@SpringBootTest
public class RequestResponseLogTest {

    @Test
    void build() {
        LocalDateTime localDateTime = LocalDateTime.now();
        RequestResponseLog requestResponseLog = RequestResponseLog.builder()
                .url("testURL")
                .method("GET")
                .localDateTime(localDateTime)
                .request("request")
                .response("response")
                .httpCode("200")
                .build();

        Assertions.assertEquals(requestResponseLog.getUrl(),"testURL");
        Assertions.assertEquals(requestResponseLog.getMethod(),"GET");
        Assertions.assertEquals(requestResponseLog.getLocalDateTime(),localDateTime);
        Assertions.assertEquals(requestResponseLog.getRequest(),"request");
        Assertions.assertEquals(requestResponseLog.getResponse(),"response");
        Assertions.assertEquals(requestResponseLog.getHttpCode(),"200");
    }

    @Test
    void constructor() {
        LocalDateTime localDateTime = LocalDateTime.now();
        RequestResponseLog requestResponseLog = new RequestResponseLog(null,"testURL","GET",localDateTime,"request","response","200");
        Assertions.assertEquals(requestResponseLog.getUrl(),"testURL");
        Assertions.assertEquals(requestResponseLog.getMethod(),"GET");
        Assertions.assertEquals(requestResponseLog.getLocalDateTime(),localDateTime);
        Assertions.assertEquals(requestResponseLog.getRequest(),"request");
        Assertions.assertEquals(requestResponseLog.getResponse(),"response");
        Assertions.assertEquals(requestResponseLog.getHttpCode(),"200");

        Assertions.assertNotNull(new RequestResponseLog());
    }

    @Test
    void settersHashCodeToSring() {

        LocalDateTime localDateTime = LocalDateTime.now();

        RequestResponseLog requestResponseLog = new RequestResponseLog(null,"","",localDateTime,"","","");
        requestResponseLog.setUrl("testURL");
        requestResponseLog.setMethod("GET");
        requestResponseLog.setLocalDateTime(localDateTime);
        requestResponseLog.setRequest("request");
        requestResponseLog.setResponse("response");
        requestResponseLog.setHttpCode("200");


        Assertions.assertEquals(requestResponseLog.getUrl(),"testURL");
        Assertions.assertEquals(requestResponseLog.getMethod(),"GET");
        Assertions.assertEquals(requestResponseLog.getLocalDateTime(),localDateTime);
        Assertions.assertEquals(requestResponseLog.getRequest(),"request");
        Assertions.assertEquals(requestResponseLog.getResponse(),"response");
        Assertions.assertEquals(requestResponseLog.getHttpCode(),"200");

        Assertions.assertNotNull(requestResponseLog.hashCode());
        Assertions.assertNotNull(requestResponseLog.toString());
    }
}
