package com.dorbit.web_crawler.config;

import com.dorbit.web_crawler.exception.OpenConnectionException;
import com.dorbit.web_crawler.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestResponseExceptionHandler {

    @ExceptionHandler(value = { ValidationException.class })
    protected ResponseEntity<String> handleValidation(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = { OpenConnectionException.class })
    protected ResponseEntity<String> handleIllegalState(RuntimeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT);
    }

    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<String> handleGenericException(RuntimeException ex) {
        return new ResponseEntity<>("Generic exception", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
