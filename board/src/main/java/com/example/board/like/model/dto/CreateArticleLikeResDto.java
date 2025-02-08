package com.example.board.like.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateArticleLikeResDto {
    private Long id;
    private LocalDateTime createdAt;
    private Long articleId;
}
