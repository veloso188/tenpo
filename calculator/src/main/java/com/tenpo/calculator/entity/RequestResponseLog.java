package com.tenpo.calculator.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class RequestResponseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String url;

    @Column
    private String method;

    @Column
    private LocalDateTime localDateTime;

    @Column
    private String requestBody;

    @Column(length = 5000)
    private String responseBody;

}
