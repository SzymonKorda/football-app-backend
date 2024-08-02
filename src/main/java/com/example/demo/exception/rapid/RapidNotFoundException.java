package com.example.demo.exception.rapid;

public class RapidNotFoundException extends RuntimeException {
    public RapidNotFoundException() {
        super("Rapid resource not found");
    }
}
