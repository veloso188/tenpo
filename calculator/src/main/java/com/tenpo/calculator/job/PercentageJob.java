package com.tenpo.calculator.job;

import com.tenpo.calculator.service.external.PercentageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class PercentageJob {
    @Autowired
    PercentageService percentageService;

    @Value("${percentage.job.enabled}")
    private boolean enabled;
    @Scheduled(fixedRateString = "${percentage.job.refresh_interval}")
    public void getPercentage() {
        if (this.enabled) {
            log.info("Starting percentage job...");
            try {
                percentageService.getPercentageFromExternalService();
                log.info("Percentage job finished ok");
            } catch (Exception e) {
                log.error("Percentage job finished with error: " + ExceptionUtils.getStackTrace(e));
            }
        }
    }
}
