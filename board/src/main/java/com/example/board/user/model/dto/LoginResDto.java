package com.example.board.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResDto {
    private String userId;
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
