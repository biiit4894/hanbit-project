package com.example.board.article.model.dto;

import com.example.board.comment.model.dto.CommentDetailDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GetArticleDetailResDto {
    private Long id;
    private String title;
    private String content;
    private Long commentCount;
    private Long likeCount;
    private String createdAt;
    private List<CommentDetailDto> comments;
    private String author;

}
