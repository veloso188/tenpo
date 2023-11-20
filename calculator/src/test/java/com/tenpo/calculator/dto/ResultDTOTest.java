package com.tenpo.calculator.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ResultDTOTest {
    private static Double TEST_RESULT = 10d;
    private static Double TEST_RESULT_SECOND_VALUE = 12d;

    @Test
    void getterSetterHashCode() {
        ResultDTO resultDTO = new ResultDTO(TEST_RESULT);
        Assertions.assertNotNull(resultDTO.hashCode());
        resultDTO.setResult(TEST_RESULT_SECOND_VALUE);
        Assertions.assertEquals(resultDTO.getResult(),TEST_RESULT_SECOND_VALUE);
    }

}

