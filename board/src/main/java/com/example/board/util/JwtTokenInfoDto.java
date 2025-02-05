package com.example.board.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class JwtTokenInfoDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
