package com.tenpo.calculator.service;


import com.tenpo.calculator.entity.RequestResponseLog;
import com.tenpo.calculator.repository.RequestResponseLogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public class LoggerDBServiceImpl implements LoggerDBService {
    @Autowired
    RequestResponseLogRepository requestResponseLogRepository;

    @Async
    public void saveRequestResponseLog(String url, String method, LocalDateTime localDateTime, String requestBody, String responseBody)
    {
        RequestResponseLog requestResponseLog;
        requestResponseLog = new RequestResponseLog(null,url,method,localDateTime,requestBody,responseBody);
        requestResponseLogRepository.save(requestResponseLog);
        log.info("Data: " +requestResponseLog );
    }
}
