package com.example.demo.exception;

import com.example.demo.exception.base.AlreadyExistsException;
import com.example.demo.exception.base.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<ExceptionResponse> handleApiNotFoundException(NotFoundException e) {
        ExceptionResponse response = prepareExceptionResponse(e);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(value = {AlreadyExistsException.class})
    public ResponseEntity<ExceptionResponse> handleApiAlreadyExistsException(AlreadyExistsException e) {
        ExceptionResponse response = prepareExceptionResponse(e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    private ExceptionResponse prepareExceptionResponse(RuntimeException e) {
        return new ExceptionResponse(e.getMessage(), ZonedDateTime.now());
    }
}
