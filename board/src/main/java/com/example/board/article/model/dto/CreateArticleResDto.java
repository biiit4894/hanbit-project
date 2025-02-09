package com.example.board.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateArticleResDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author; // author's nickname
}
