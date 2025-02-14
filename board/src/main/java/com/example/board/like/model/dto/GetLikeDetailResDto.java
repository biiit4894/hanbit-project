package com.example.board.like.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetLikeDetailResDto {
    private Long id;
    private String createdAt;
    private Long articleId;
    private String author;
}
