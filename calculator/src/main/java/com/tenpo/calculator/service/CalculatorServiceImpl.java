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

//    public CalculatorServiceImpl(PercentageService percentageService) {
//        this.percentageService = percentageService;
//    }

    //Caculates the result of the operation
    //operand1 + operand2 + percentageReceivedOf(operand1 + operand2)
    private ResultDTO calculateResult(Double operand1, Double operand2) {
        return new ResultDTO((operand1 + operand2) * (100d + percentageService.getPercentage()) / 100d);
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
