package com.example.Asum_BE.common.dto.responseDto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ApiResponseDto<T> {

    private String state;
    private int code;
    private String message;
    private T data;

    public ApiResponseDto(String state, int code, String message, T data) {
        this.state = state;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ApiResponseDto<T> success(int code, String message, T data) {
        return new ApiResponseDto<>("SUCCESS", code, message, data);
    }

    public static <T> ApiResponseDto<T> fail(int code, String message) {
        return new ApiResponseDto<>("FAIL", code, message, null);
    }
}
