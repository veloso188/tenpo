package com.tenpo.calculator.error.dto;

import lombok.*;

import java.time.LocalDateTime;
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class ExceptionMessageDTO {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;

}
