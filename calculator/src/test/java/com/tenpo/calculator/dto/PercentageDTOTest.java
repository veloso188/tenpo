package com.tenpo.calculator.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PercentageDTOTest {
    private static Double TEST_PERCENTAGE = 10d;
    private static Double TEST_PERCENTAGE_SECOND_VALUE = 12d;

    @Test
    void getterSetterHashCode() {
        PercentageDTO percentageDTO = new PercentageDTO(TEST_PERCENTAGE);
        Assertions.assertNotNull(percentageDTO.hashCode());
        percentageDTO.setPercentage(TEST_PERCENTAGE_SECOND_VALUE);
        Assertions.assertEquals(percentageDTO.getPercentage(), TEST_PERCENTAGE_SECOND_VALUE);
    }
    @Test
    void noArgsConsgrucutor() {
        PercentageDTO percentageDTO = new PercentageDTO();
        Assertions.assertNotNull(percentageDTO);
    }

}

