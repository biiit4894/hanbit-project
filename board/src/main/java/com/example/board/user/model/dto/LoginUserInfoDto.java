package com.example.board.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LoginUserInfoDto {
    private Long id;
    private String userId;
    private String nickName;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    // TODO : articleList 추가
}
