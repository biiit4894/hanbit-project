package com.example.board.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignupResDto {
    private String userId;
    private String userName;
    private String email;
}
