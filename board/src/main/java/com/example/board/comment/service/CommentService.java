package com.example.board.comment.service;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.comment.model.dto.*;
import com.example.board.comment.model.entity.Comment;
import com.example.board.comment.repository.CommentRepository;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
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

        Comment comment = new Comment(
                reqDto.getContent(),
                article,
                parent,
                loginUser
        );
        commentRepository.save(comment);

        article.increaseCommentCount();
        articleRepository.save(article);
        return new CreateCommentResDto(comment);
    }

    @Transactional
    public UpdateCommentResDto updateComment(Long id, UpateCommentReqDto reqDto) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No Comment Found"));
        String authorUserId = comment.getUser().getUserId();
        String loginUserId = userService.getLoginUserInfo().getUserId();
        if (!Objects.equals(authorUserId, loginUserId)) {
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

        if(!comment.getReplies().isEmpty()) {
            comment.markDeleted(); // 부모 댓글은 삭제하지 않는다.
        } else {
            commentRepository.deleteById(id);
        }
        article.decreaseCommentCount();
    }

    @Transactional
    public List<CommentDetailDto> getParentCommentsByArticleId(Long id) {
        // 부모 댓글들과 그 자식 댓글들을 모두 포함하는 목록

        List<Comment> parentComments = commentRepository.findParentsByArticleId(id);
        List<CommentDetailDto> comments = new ArrayList<>();

        for (Comment parentComment : parentComments) {
            // 부모 댓글의 상세 내역 조회
            CommentDetailDto parentCommentDto = new CommentDetailDto(parentComment);

            List<Comment> replies = commentRepository.findRepliesByParentId(parentComment.getId());
            List<CommentDetailDto> replyDtos = new ArrayList<>();
            for (Comment reply : replies) {
                replyDtos.add(new CommentDetailDto(reply));
            }

            parentCommentDto.setReplies(replyDtos);
            comments.add(parentCommentDto);
        }

        return comments;
    }
}
