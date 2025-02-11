package com.example.board.exception;

import lombok.Getter;

import java.io.Serial;

@Getter
public class InvalidPasswordException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPasswordException(String message) {
        super(message);
    }
}
