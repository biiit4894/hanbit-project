package com.example.board.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.List;
import java.util.Map;

@Getter
public class CustomValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, List<String>> errorMap;

    public CustomValidationException(String message, Map<String, List<String>> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }
}
