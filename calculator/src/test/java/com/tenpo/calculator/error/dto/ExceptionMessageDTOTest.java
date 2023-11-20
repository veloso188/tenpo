package com.tenpo.calculator.error.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

public class ExceptionMessageDTOTest {
    @Test
    void getterSetterHashCode() {
        LocalDateTime localDateTime = LocalDateTime.now();
        ExceptionMessageDTO exceptionMessageDTO = new ExceptionMessageDTO(localDateTime,
                200,
                "error",
                "msg",
                "path");

        Assertions.assertEquals(exceptionMessageDTO.getTimestamp(),localDateTime);
        Assertions.assertEquals(exceptionMessageDTO.getStatus(),200);
        Assertions.assertEquals(exceptionMessageDTO.getError(),"error");
        Assertions.assertEquals(exceptionMessageDTO.getMessage(),"msg");
        Assertions.assertEquals(exceptionMessageDTO.getPath(),"path");


        localDateTime = LocalDateTime.now();
        Assertions.assertNotNull(exceptionMessageDTO.hashCode());
        Assertions.assertNotNull(exceptionMessageDTO.toString());

        exceptionMessageDTO.setTimestamp(localDateTime);
        exceptionMessageDTO.setStatus(300);
        exceptionMessageDTO.setError("error2");
        exceptionMessageDTO.setMessage("msg2");
        exceptionMessageDTO.setPath("path2");

        Assertions.assertEquals(exceptionMessageDTO.getTimestamp(),localDateTime);
        Assertions.assertEquals(exceptionMessageDTO.getStatus(),300);
        Assertions.assertEquals(exceptionMessageDTO.getError(),"error2");
        Assertions.assertEquals(exceptionMessageDTO.getMessage(),"msg2");
        Assertions.assertEquals(exceptionMessageDTO.getPath(),"path2");
    }

}
