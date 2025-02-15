package com.example.board.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateArticleResDto {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String author; // author's nickname
}
