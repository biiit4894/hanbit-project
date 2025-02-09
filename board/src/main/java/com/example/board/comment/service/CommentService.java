package com.example.board.comment.service;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.comment.model.Comment;
import com.example.board.comment.model.CreateCommentReqDto;
import com.example.board.comment.model.CreateCommentResDto;
import com.example.board.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public CreateCommentResDto createComment(CreateCommentReqDto reqDto) {
        Article article = articleRepository.findById(reqDto.getArticleId()).orElseThrow(() -> new IllegalArgumentException("No Article Found"));

        Comment parent = null;
        if (reqDto.getParentId() != null) {
            parent = commentRepository.findById(reqDto.getParentId()).orElseThrow(() -> new IllegalArgumentException("No Parent Comment Found"));
        }

        Comment comment = new Comment(reqDto.getContent(), article, parent);
        commentRepository.save(comment);

        article.increaseCommentCount();
        return new CreateCommentResDto(comment);
    }
}
