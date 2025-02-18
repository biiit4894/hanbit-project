package com.example.board.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class DuplicateUserInputException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateUserInputException(String message) {
        super(message);
    }
}
