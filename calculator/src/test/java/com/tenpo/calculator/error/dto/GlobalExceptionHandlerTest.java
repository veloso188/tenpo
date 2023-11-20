package com.tenpo.calculator.error.dto;

import com.jayway.jsonpath.internal.function.Parameter;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.tenpo.calculator.error.exception.GlobalExceptionHandler;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.mockito.Mockito.when;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.lang.reflect.Method;

@SpringBootTest
public class GlobalExceptionHandlerTest {

    @Mock
    HttpServletRequest httpServletRequestMock;
    @Mock
    Exception exceptionMock;
    @Mock
    HttpClientErrorException httpClientErrorExceptionMock;
    @Mock
    MethodArgumentTypeMismatchException methodArgumentTypeMismatchExceptionMock;
    @Mock
    MissingServletRequestParameterException missingServletRequestParameterExceptionMock;

    @Mock
    MethodParameter MethodParameterMock;

    @Test
    void handleException() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        when(httpServletRequestMock.getServletPath()).thenReturn("/api");
        ResponseEntity<ExceptionMessageDTO> response = globalExceptionHandler.defaultException(httpServletRequestMock, exceptionMock);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
        Assertions.assertEquals(response.getBody().getPath(),"/api");
        Assertions.assertEquals(response.getBody().getMessage(),"Error");
        Assertions.assertEquals(response.getBody().getError(),HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        Assertions.assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void httpClientErrorExceptionMock() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        when(httpServletRequestMock.getServletPath()).thenReturn("/api");
        when(httpClientErrorExceptionMock.getStatusCode()).thenReturn(HttpStatus.SERVICE_UNAVAILABLE);

        ResponseEntity<ExceptionMessageDTO> response = globalExceptionHandler.httpClientErrorException(httpServletRequestMock, httpClientErrorExceptionMock);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.SERVICE_UNAVAILABLE);
        Assertions.assertEquals(response.getBody().getPath(),"/api");
        Assertions.assertEquals(response.getBody().getMessage(),"Error");
        Assertions.assertEquals(response.getBody().getError(),HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase());
        Assertions.assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void missingServletRequestParameterException() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        when(httpServletRequestMock.getServletPath()).thenReturn("/api");

        ResponseEntity<ExceptionMessageDTO> response = globalExceptionHandler.missingServletRequestParameterException(httpServletRequestMock, missingServletRequestParameterExceptionMock);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(response.getBody().getPath(),"/api");
        Assertions.assertEquals(response.getBody().getMessage(),null);
        Assertions.assertEquals(response.getBody().getError(),HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertNotNull(response.getBody().getTimestamp());
    }

    @Test
    void methodArgumentNotValidException() {

        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
        when(httpServletRequestMock.getServletPath()).thenReturn("/api");
        when(methodArgumentTypeMismatchExceptionMock.getParameter()).thenReturn(MethodParameterMock);
        when(MethodParameterMock.getParameterName()).thenReturn("mockParameterName");

        ResponseEntity<ExceptionMessageDTO> response = globalExceptionHandler.methodArgumentTypeMismatchException(httpServletRequestMock, methodArgumentTypeMismatchExceptionMock);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        Assertions.assertEquals(response.getBody().getPath(),"/api");
        Assertions.assertEquals(response.getBody().getMessage(),"Wrong data type for: " + "mockParameterName");
        Assertions.assertEquals(response.getBody().getError(),HttpStatus.BAD_REQUEST.getReasonPhrase());
        Assertions.assertNotNull(response.getBody().getTimestamp());
    }

}
