package com.example.demo.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public record ExceptionResponse(
        String message,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        ZonedDateTime timestamp) {
}
