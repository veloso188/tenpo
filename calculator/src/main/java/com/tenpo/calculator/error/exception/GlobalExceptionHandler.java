package com.tenpo.calculator.error.exception;

import com.tenpo.calculator.error.dto.ExceptionMessageDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;


@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String GENERIC_ERROR_MESSAGE = "Error";
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<ExceptionMessageDTO> httpClientErrorException (HttpServletRequest httpServletRequest, HttpClientErrorException httpClientErrorException) {
        ExceptionMessageDTO response = new ExceptionMessageDTO(
                LocalDateTime.now(),
                httpClientErrorException.getStatusCode().value(),
                HttpStatus.valueOf(httpClientErrorException.getStatusCode().value()).getReasonPhrase(),
                GENERIC_ERROR_MESSAGE,
                httpServletRequest.getServletPath()
        );
        log.error(httpClientErrorException.getMessage(), httpClientErrorException);
        return new ResponseEntity<ExceptionMessageDTO> (response, HttpStatus.valueOf(httpClientErrorException.getStatusCode().value())) ;
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessageDTO> defaultException (HttpServletRequest httpServletRequest, MethodArgumentTypeMismatchException methodArgumentNotValidException) {
        ExceptionMessageDTO response = new ExceptionMessageDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                 "Wrong data type for: " + methodArgumentNotValidException.getParameter().getParameterName(),
                httpServletRequest.getServletPath()
        );
        log.error(methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
        return new ResponseEntity<ExceptionMessageDTO> (response, HttpStatus.BAD_REQUEST) ;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionMessageDTO> defaultException (HttpServletRequest httpServletRequest, MissingServletRequestParameterException missingServletRequestParameterException) {
        ExceptionMessageDTO response = new ExceptionMessageDTO(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                missingServletRequestParameterException.getMessage(),
                httpServletRequest.getServletPath()
        );
        log.error(missingServletRequestParameterException.getMessage(), missingServletRequestParameterException);
        return new ResponseEntity<ExceptionMessageDTO> (response, HttpStatus.BAD_REQUEST) ;
    }
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ExceptionMessageDTO> defaultException (HttpServletRequest httpServletRequest, Exception exception) {
        ExceptionMessageDTO response = new ExceptionMessageDTO(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                GENERIC_ERROR_MESSAGE,
                httpServletRequest.getServletPath()
        );
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<ExceptionMessageDTO> (response, HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

}
