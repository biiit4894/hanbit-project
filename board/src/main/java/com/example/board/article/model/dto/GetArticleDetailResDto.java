package com.example.board.article.model.dto;

import com.example.board.article.model.entity.Article;
import com.example.board.comment.model.dto.CommentDetailDto;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class GetArticleDetailResDto {
    private Long id;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;
    private LocalDateTime createdAt;
    private List<CommentDetailDto> comments;
    private String author;

    public GetArticleDetailResDto(Article article, List<CommentDetailDto> comments) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.commentCount = article.getCommentCount();
        this.likeCount = article.getLikeCount();
        this.createdAt = article.getCreatedAt();
        this.comments = comments;
        this.author = null; // TODO : 작성자 반영
    }

}
