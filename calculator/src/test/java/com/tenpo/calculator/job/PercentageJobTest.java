package com.tenpo.calculator.job;


import com.tenpo.calculator.service.external.PercentageServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import static org.mockito.Mockito.doNothing;

@SpringBootTest
@TestPropertySource(properties = "percentage.job.enabled=true")

public class PercentageJobTest {

    @InjectMocks
    PercentageJob percentageJobMock;
    @Mock
    PercentageServiceImpl PercentageServiceImplMock;

    @Test
    void percentageJobTest() {
        doNothing().when(PercentageServiceImplMock).getPercentageFromExternalService();
        percentageJobMock.getPercentage();
    }
}
