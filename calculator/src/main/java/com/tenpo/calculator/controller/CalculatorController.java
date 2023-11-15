package com.tenpo.calculator.controller;

import com.tenpo.calculator.dto.ResultDTO;
import com.tenpo.calculator.service.CalculatorService;
import com.tenpo.calculator.service.LoggerDBService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.Duration;




@RestController
@RequestMapping("/api/v1/calculator")
public class CalculatorController {
    @Autowired CalculatorService calculatorService;
    @Autowired
    LoggerDBService loggerDBService;
    private Bucket bucket;


    @Value("${calculator.service.max_tries_per_minute}")
    private int max_tries_per_minute;

    @PostConstruct
    private void configureBucket() {
        Refill refill = Refill.intervally(max_tries_per_minute, Duration.ofMinutes(1));
        Bandwidth limit =  Bandwidth.classic(max_tries_per_minute,refill);
        this.bucket = Bucket.builder()
                .addLimit(limit)
                .build();
    }

    @GetMapping("/getValue")
    public ResponseEntity<Page<ResultDTO>> getValue(@RequestParam  Double operand1, @RequestParam  Double operand2, Pageable pageable) {
        if (!bucket.tryConsume(1)) throw new HttpClientErrorException(HttpStatus.TOO_MANY_REQUESTS);
        return new ResponseEntity<Page<ResultDTO>> (calculatorService.getValue(operand1, operand2, pageable), HttpStatus.OK);
    }
}
