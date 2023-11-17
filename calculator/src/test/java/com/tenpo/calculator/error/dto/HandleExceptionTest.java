package com.tenpo.calculator.error.dto;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.tenpo.calculator.error.exception.GlobalExceptionHandler;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;
@SpringBootTest
public class HandleExceptionTest {

    @Mock
    HttpServletRequest httpServletRequestMock;
    @Mock
    Exception exceptionMock;
    @Test
    void handleExceptions() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        when(httpServletRequestMock.getServletPath()).thenReturn("/api");
        ResponseEntity<ExceptionMessageDTO> response = globalExceptionHandler.defaultException(httpServletRequestMock, exceptionMock);
        System.out.println(response);
        Assertions.assertEquals(response.getStatusCode().value(),500);
        Assertions.assertEquals(response.getBody().getPath(),"/api");
        Assertions.assertEquals(response.getBody().getMessage(),"Error");
        Assertions.assertEquals(response.getBody().getError(),"Internal Server Error");
        Assertions.assertNotNull(response.getBody().getTimestamp());
    }


}
