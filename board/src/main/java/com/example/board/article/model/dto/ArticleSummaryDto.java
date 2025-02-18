package com.example.board.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleSummaryDto {
    private Long id;
    private String title;
    private Long commentCount;
    private Long likeCount;
    private String createdAt;
    private String author;
    private int displayNumber;
}
