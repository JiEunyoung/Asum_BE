package com.example.Asum_BE.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidCommentException extends RuntimeException {

    private final int errorCode;
    private final HttpStatus httpStatus;

    public InvalidCommentException(int errorCode, String message, HttpStatus httpStatus) {
        super(message);
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
    }
}
