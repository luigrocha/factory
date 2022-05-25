package org.crsoft.cartonplast.config.handler;

import org.crsoft.cartonplast.config.exception.ConfigWsGlobalException;
import org.crsoft.cartonplast.config.vo.res.ErrorRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

/**
 * @author lpillaga on 19/05/2022
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ConfigWsGlobalException.class)
    protected ResponseEntity handleGlobalException(ConfigWsGlobalException globalException, Locale locale) {
        return ResponseEntity
                .badRequest()
                .body(ErrorRes.builder()
                        .code(globalException.getCode())
                        .message(globalException.getMessage())
                        .build());
    }

    @ExceptionHandler({Exception.class})
    protected ResponseEntity handleException(Exception e, Locale locale) {
        return ResponseEntity
                .badRequest()
                .body("Error interno servicio configuración" + e);
    }

}