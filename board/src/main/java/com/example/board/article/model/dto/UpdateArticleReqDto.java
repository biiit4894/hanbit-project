package com.example.board.article.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateArticleReqDto {
    private String title;
    private String content;
}
