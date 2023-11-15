package com.tenpo.calculator.service;


import java.time.LocalDateTime;

public interface LoggerDBService {
    public void saveRequestResponseLog(String url, String method, LocalDateTime localDateTime, String requestBody, String responseBody);


}
