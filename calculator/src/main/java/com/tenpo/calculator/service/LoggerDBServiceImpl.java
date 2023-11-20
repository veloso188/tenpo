package com.tenpo.calculator.service;


import com.tenpo.calculator.entity.RequestResponseLog;
import com.tenpo.calculator.repository.RequestResponseLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Service
public class LoggerDBServiceImpl implements LoggerDBService {
    @Autowired
    RequestResponseLogRepository requestResponseLogRepository;

    @Async
    public void saveRequestResponseLog(String url, String method, LocalDateTime localDateTime, String request, String response, String httpCode)
    {
        try {
            RequestResponseLog requestResponseLog = RequestResponseLog
                    .builder()
                    .url(url)
                    .method(method)
                    .localDateTime(localDateTime)
                    .request(request)
                    .response(response)
                    .httpCode(httpCode)
                    .build();
            requestResponseLogRepository.save(requestResponseLog);
            log.info("Data: " +requestResponseLog );
        } catch (Exception e) {
            log.error("Error saving RequestResponseLog in DB");
            log.error(ExceptionUtils.getStackTrace(e));
        }

    }
}
