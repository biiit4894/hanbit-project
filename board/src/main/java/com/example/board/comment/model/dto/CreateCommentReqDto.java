package com.example.board.comment.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateCommentReqDto {
    private Long articleId;
    private Long parentId;
    private String content;
}
