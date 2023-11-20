package com.tenpo.calculator.service.external;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tenpo.calculator.dto.PercentageDTO;
import com.tenpo.calculator.service.LoggerDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
public class PercentageServiceImpl implements PercentageService {
    private Double lastPercentageValue;
    @Value("${percentage.service.url}")
    private String percentageServiceURL;
    private static String PERCENTAGE_SERVICE_ERROR_MSG = "Percentage service is down and no cached value to return";

    @Autowired
    LoggerDBService loggerDBService;

    private RestTemplate restTemplateForTest;


    public RestTemplate getRestTemplate() {
        if (restTemplateForTest==null) return new RestTemplate();
        else return restTemplateForTest;
    }

    public void setRestTemplateForTest(RestTemplate restTemplateForTest) {
        this.restTemplateForTest = restTemplateForTest;
    }

    // This method is for getting the information from the external service, the retry policy is managed here
    @Override
    @Retryable(retryFor = { Exception.class },
            maxAttemptsExpression = "${percentage.service.max_attempts}",
            backoff = @Backoff(delayExpression = "${percentage.service.retry_time}"))
    public void getPercentageFromExternalService ()  {
        log.info("+Percentage Service");
        ResponseEntity<String> response = null;
        try {
            //RestTemplate restTemplate = new RestTemplate();
            RestTemplate restTemplate = this.getRestTemplate();
            response = restTemplate.getForEntity(percentageServiceURL , String.class);

            PercentageDTO percentageDTO;
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = null;
            root = mapper.readTree(response.getBody());
            percentageDTO = mapper.readValue(root.get("content").get(0).toString(), PercentageDTO.class);

            lastPercentageValue = percentageDTO.getPercentage();
            log.info("Response Received: " + lastPercentageValue);
            loggerDBService.saveRequestResponseLog(percentageServiceURL, "GET", LocalDateTime.now(),"", response.getBody(),response.getStatusCode().toString());
            log.info("-Percentage Service");
        } catch (Exception e) {
            if (response!=null) loggerDBService.saveRequestResponseLog(percentageServiceURL, "GET", LocalDateTime.now(), "" , response.getBody(), response.getStatusCode().toString());
                else loggerDBService.saveRequestResponseLog(percentageServiceURL, "GET", LocalDateTime.now(),"", "","");
            throw new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, PERCENTAGE_SERVICE_ERROR_MSG);
        }

        return;

    }
    //After no success retry whe throw the exception
    @Recover
    public void recover(Exception exception) {
        log.error("Percentage service is off line");
        throw new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, PERCENTAGE_SERVICE_ERROR_MSG);
    }

    //getPercentage returns the value of the last percentage received or an error if no value loaded
    public Double getPercentage () {
        if (lastPercentageValue==null) throw new HttpClientErrorException(HttpStatus.SERVICE_UNAVAILABLE, PERCENTAGE_SERVICE_ERROR_MSG);
        return lastPercentageValue;
    }
}
