package com.example.Asum_BE.exception;

public class InvalidPostException extends RuntimeException {
    public InvalidPostException(String message) {
        super(message);
    }
}
