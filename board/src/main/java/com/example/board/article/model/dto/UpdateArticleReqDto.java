package com.example.board.article.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateArticleReqDto {
    @Size(min=5, max=30, message = "5자 이상 30자 이하이어야 합니다.")
    private String title;

    @Size(min=5, max=1000, message = "5자 이상 1000자 이하이어야 합니다.")
    private String content;
}
