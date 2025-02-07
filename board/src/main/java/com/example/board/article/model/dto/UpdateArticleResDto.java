package com.example.board.article.model.dto;

import com.example.board.article.model.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateArticleResDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    // TODO : updatedAt 정보도 정말 필요할지 고민
    // TODO : 작성자 정보 포함

    public UpdateArticleResDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.updatedAt = LocalDateTime.now();
    }
}
