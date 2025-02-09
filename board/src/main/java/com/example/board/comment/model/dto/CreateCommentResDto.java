package com.example.board.comment.model.dto;

import com.example.board.comment.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class CreateCommentResDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
    private Long articleId;
    private Long parentId;
    private String author; // author nickname

    public CreateCommentResDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.isDeleted = comment.getIsDeleted();
        this.articleId = comment.getArticle().getId();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
        this.author = comment.getUser().getNickName();
    }
}
