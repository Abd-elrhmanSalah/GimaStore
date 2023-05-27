package com.gima.gimastore.exception;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        StatusResponse error = new StatusResponse("500", "INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String defaultMessage = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        String message;
        String code;
        if (defaultMessage != null && defaultMessage.contains("':::'")) {
            message = defaultMessage.split("':::'")[0];
            code = defaultMessage.split("':::'")[1];
        } else {
            message = defaultMessage;
            code = "400";
        }
        StatusResponse error = new StatusResponse(code, "Bad Request", message);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }

}
