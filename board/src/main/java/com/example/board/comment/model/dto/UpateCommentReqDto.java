package com.example.board.comment.model.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpateCommentReqDto {
    @Size(min = 1, max = 500, message="1자 이상 500자 이하여야 합니다.")
    private String content;
}
