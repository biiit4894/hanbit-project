package com.example.board.comment.model.dto;

import com.example.board.comment.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateCommentResDto {
    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;
    private Long articleId;
    private Long parentId;

    public UpdateCommentResDto(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
        this.deletedAt = comment.getDeletedAt();
        this.articleId = comment.getArticle().getId();
        if (comment.getParent() != null) {
            this.parentId = comment.getParent().getId();
        }
    }

}
