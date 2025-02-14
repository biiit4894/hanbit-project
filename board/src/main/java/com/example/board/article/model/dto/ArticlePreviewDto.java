package com.example.board.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticlePreviewDto {
    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String author;
}
