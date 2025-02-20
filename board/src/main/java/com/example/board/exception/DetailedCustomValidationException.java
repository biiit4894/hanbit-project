package com.example.board.exception;

import lombok.Getter;

import java.io.Serial;
import java.util.List;
import java.util.Map;

@Getter
public class DetailedCustomValidationException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Map<String, List<String>> errorMap;

    public DetailedCustomValidationException(String message, Map<String, List<String>> errorMap) {
        super(message);
        this.errorMap = errorMap;
    }
}
