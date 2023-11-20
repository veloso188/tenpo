package com.tenpo.mockserver.controller;

import com.tenpo.mockserver.dto.PercentageDTO;
import com.tenpo.mockserver.service.PercentageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/percentage")
public class MockServerController {
    @Autowired
    PercentageService percentageService;
    @GetMapping("/getPercentage")
    public ResponseEntity<Page<PercentageDTO>> getPercentage(Pageable pageable){
        return new ResponseEntity<Page<PercentageDTO>>(percentageService.getPercentage(pageable),null, HttpStatus.OK);
    }

}
