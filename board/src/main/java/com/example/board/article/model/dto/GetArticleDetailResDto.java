package com.example.board.article.model.dto;

import com.example.board.article.model.entity.Article;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetArticleDetailResDto {
    private Long id;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    // TODO : 댓글, 좋아요 필드 추가
    private String author;

    public GetArticleDetailResDto(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.commentCount = article.getCommentCount();
        this.likeCount = article.getLikeCount();
        this.createdAt = article.getCreatedAt();
        this.author = null; // TODO : 작성자 반영
    }

}
