package com.example.board.comment.service;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.comment.model.dto.UpateCommentReqDto;
import com.example.board.comment.model.dto.UpdateCommentResDto;
import com.example.board.comment.model.entity.Comment;
import com.example.board.comment.model.dto.CreateCommentReqDto;
import com.example.board.comment.model.dto.CreateCommentResDto;
import com.example.board.comment.repository.CommentRepository;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final ArticleRepository articleRepository;

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    @Transactional
    public CreateCommentResDto createComment(CreateCommentReqDto reqDto) {
        Article article = articleRepository.findById(reqDto.getArticleId()).orElseThrow(() -> new IllegalArgumentException("No Article Found"));

        Comment parent = null;
        if (reqDto.getParentId() != null) {
            parent = commentRepository.findById(reqDto.getParentId()).orElseThrow(() -> new IllegalArgumentException("No Parent Comment Found"));
        }

        Long id = userService.getLoginUserInfo().getId();
        User loginUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("User Not Found"));

        Comment comment = new Comment(reqDto.getContent(), article, parent, loginUser);
        commentRepository.save(comment);

        article.increaseCommentCount();
        return new CreateCommentResDto(comment);
    }

    @Transactional
    public UpdateCommentResDto updateComment(Long id, UpateCommentReqDto reqDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Comment Found"));
        String authorUserId = comment.getUser().getUserId();
        String loginUserId = userService.getLoginUserInfo().getUserId();
        if (!authorUserId.equals(loginUserId)) {
            throw new AccessDeniedException("no permission to update comment");
        }
        comment.updateContent(reqDto.getContent());
        commentRepository.save(comment);
        return new UpdateCommentResDto(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Comment Not Found"));
        Article article = articleRepository.findById(comment.getArticle().getId()).orElseThrow(() -> new IllegalArgumentException("Article Not Found"));
        String authorUserId = comment.getUser().getUserId();
        String loginUserId = userService.getLoginUserInfo().getUserId();
        if (!authorUserId.equals(loginUserId)) {
            throw new AccessDeniedException("no permission to delete comment");
        }

        if(comment.getParent() != null) {
            commentRepository.deleteById(id);
        }
        comment.markDeleted();
        article.decreaseCommentCount();
    }
}
