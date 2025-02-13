package com.example.board.comment.model.dto;

import com.example.board.comment.model.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentDetailDto {
    private Long id;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isDeleted;
    private Long articleId;
    private List<CommentDetailDto> replies = new ArrayList<>();

    // 부모 댓글의 상세 내역 조회
    public CommentDetailDto(Comment parentComment) {
        this.id = parentComment.getId();
        this.content = parentComment.getContent();
        this.author = parentComment.getUser().getNickName();
        this.createdAt = parentComment.getCreatedAt();
        this.updatedAt = parentComment.getUpdatedAt();
        this.isDeleted = parentComment.getIsDeleted();
        this.articleId = parentComment.getArticle().getId();
    }

    public void setReplies(List<CommentDetailDto> replies) {
        this.replies = replies;
    }
}
