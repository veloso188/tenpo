package com.tenpo.calculator.service.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.calculator.dto.PercentageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class PercentageServiceImpl implements PercentageService {
    private Double lastPercentageValue;

    private static String PERCENTAGE_SERVICE_URL = "http://localhost:8081/api/v1/percentage/getPercentage";
    private static String PERCENTAGE_SERVICE_ERROR_MSG = "Percentage service is down and no cached value to return";
    @Override
    @Retryable(retryFor = { Exception.class },
            maxAttempts = 3,
            backoff = @Backoff(delay = 1000))
    public Double getPercentage ()  {
        log.info("+Percentage Service");
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(PERCENTAGE_SERVICE_URL , String.class);

        PercentageDTO percentageDTO;
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;

        try {
            root = mapper.readTree(response.getBody());
            percentageDTO = mapper.readValue(root.get("content").get(0).toString(), PercentageDTO.class);
        } catch (JsonProcessingException e) {
            throw new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, PERCENTAGE_SERVICE_ERROR_MSG);
        }

        lastPercentageValue = percentageDTO.getPercentage();
        log.info("Response Received: " + lastPercentageValue);
        log.info("-Percentage Service");
        return lastPercentageValue;

    }
    @Recover
    public Double recover(Exception exception) {
        if (lastPercentageValue==null) {
            log.error(PERCENTAGE_SERVICE_ERROR_MSG);
            throw new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, PERCENTAGE_SERVICE_ERROR_MSG);
        }
        else {
            log.error("Percentage service is off line, using last value: " + lastPercentageValue);
            return lastPercentageValue;
        }
    };
}
