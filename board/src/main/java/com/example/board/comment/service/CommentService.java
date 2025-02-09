package com.example.board.comment.service;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.comment.model.dto.UpateCommentReqDto;
import com.example.board.comment.model.dto.UpdateCommentResDto;
import com.example.board.comment.model.entity.Comment;
import com.example.board.comment.model.dto.CreateCommentReqDto;
import com.example.board.comment.model.dto.CreateCommentResDto;
import com.example.board.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Transactional
    public UpdateCommentResDto updateComment(Long id, UpateCommentReqDto reqDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Comment Found"));
        comment.updateContent(reqDto.getContent());
        commentRepository.save(comment);
        return new UpdateCommentResDto(comment);
    }
}
