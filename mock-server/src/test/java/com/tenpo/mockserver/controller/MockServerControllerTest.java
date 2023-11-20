package com.tenpo.mockserver.controller;

import com.tenpo.mockserver.dto.PercentageDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
public class MockServerControllerTest {

    @Value("${mock_server.percentage}")
    private Double percentage;
    @Autowired
    MockServerController mockServerController;
    @Test
    public void testController() {
        PercentageDTO percentageDTO = new PercentageDTO(percentage);
        PageRequest pageRequest = PageRequest.of(1, 10, Sort.unsorted());
        Assertions.assertEquals(mockServerController.getPercentage(pageRequest).getBody().getContent().get(0),percentageDTO);
    }
}
