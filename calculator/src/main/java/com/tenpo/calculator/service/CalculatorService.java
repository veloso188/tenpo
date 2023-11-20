package com.tenpo.calculator.service;


import com.tenpo.calculator.dto.ResultDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CalculatorService {
    public Page<ResultDTO> getValue(Double operand1, Double operand2, Pageable pageable);
}
