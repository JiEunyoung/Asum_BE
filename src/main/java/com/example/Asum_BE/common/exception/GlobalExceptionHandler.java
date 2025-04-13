package com.example.Asum_BE.common.exception;

import com.example.Asum_BE.common.dto.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidPostException.class)
    public ResponseEntity<ApiResponseDto<?>> handleInvalidPostException(InvalidPostException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponseDto.fail(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(InvalidCommentException.class)
    public ResponseEntity<ApiResponseDto<?>> handleInvalidCommentException(InvalidCommentException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponseDto.fail(ex.getErrorCode(), ex.getMessage()));
    }

    @ExceptionHandler(InvalidChatException.class)
    public ResponseEntity<ApiResponseDto<?>> handleInvalidChatException(InvalidChatException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponseDto.fail(ex.getErrorCode(), ex.getMessage()));

    }

    @ExceptionHandler(InvalidQuoteException.class)
    public ResponseEntity<ApiResponseDto<?>> handleInvalidQuoteException(InvalidQuoteException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(ApiResponseDto.fail(ex.getErrorCode(), ex.getMessage()));

    }
}
