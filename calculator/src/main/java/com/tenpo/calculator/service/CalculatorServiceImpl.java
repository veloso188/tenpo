package com.tenpo.calculator.service;

import com.tenpo.calculator.dto.ResultDTO;
import com.tenpo.calculator.service.external.PercentageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CalculatorServiceImpl implements CalculatorService {

    @Autowired
    PercentageService percentageService;

    private ResultDTO calculateResult(Double operand1, Double operand2) {
        return new ResultDTO(operand1 * operand2 * (percentageService.getPercentage()) * 0.01d);
    }

    @Override
    public Page<ResultDTO> getValue(Double operand1, Double operand2, Pageable pageable) {
        PageImpl<ResultDTO> pageResultDTO;
        ArrayList<ResultDTO> resultDTOList = new ArrayList<ResultDTO>();
        resultDTOList.add(calculateResult(operand1, operand2));
        pageResultDTO = new PageImpl<ResultDTO>(resultDTOList, pageable,resultDTOList.size());
        return pageResultDTO;
    }
}
