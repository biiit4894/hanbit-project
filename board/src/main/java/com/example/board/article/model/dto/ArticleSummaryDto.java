package com.example.board.article.model.dto;

import com.example.board.article.model.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ArticleSummaryDto {
    private Long id;
    private String title;
    private Long commentCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    private String author;

    public ArticleSummaryDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.commentCount = article.getCommentCount();
        this.likeCount = article.getLikeCount();
        this.createdAt = article.getCreatedAt();
//        this.author = article.getUser().getUserName();
    }
}
