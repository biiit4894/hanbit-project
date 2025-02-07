package com.example.board.user.model.dto;

import com.example.board.user.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignoutResDto {
    private String userId;
    private LocalDateTime deletedAt;

    public SignoutResDto(User user) {
        this.userId = user.getUserId();
        this.deletedAt = user.getDeletedAt();
    }
}
