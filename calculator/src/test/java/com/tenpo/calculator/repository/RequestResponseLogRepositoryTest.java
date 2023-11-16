package com.tenpo.calculator.repository;

import com.tenpo.calculator.entity.RequestResponseLog;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
@SpringBootTest
public class RequestResponseLogRepositoryTest {

    @Autowired
    RequestResponseLogRepository requestResponseLogRepository;

    @Test
    void save() {
        RequestResponseLog requestResponseLog = RequestResponseLog
                .builder()
                .url("testURL")
                .method("Get")
                .localDateTime(LocalDateTime.now())
                .request("resquest")
                .response("resposne")
                .httpCode("200")
                .build();
        Assertions.assertNotNull(requestResponseLogRepository.save(requestResponseLog));
    }

}
