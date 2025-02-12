package com.example.board.article.service;

import com.example.board.article.model.dto.*;
import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.comment.model.dto.CommentDetailDto;
import com.example.board.comment.model.entity.Comment;
import com.example.board.comment.repository.CommentRepository;
import com.example.board.user.model.entity.User;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final CommentRepository commentRepository;
    private final UserService userService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public Page<ArticleSummaryDto> getArticleList(int page) {
        Pageable pageable = PageRequest.of(page, 10);
        Page<Article> articles = articleRepository.findAll(pageable);
        List<ArticleSummaryDto> articleSummaryList = new ArrayList<>();
        for (Article article : articles.getContent()) {
            articleSummaryList.add(
                    new ArticleSummaryDto(
                            article.getId(),
                            article.getTitle(),
                            article.getCommentCount(),
                            article.getLikeCount(),
                            article.getCreatedAt().format(dateTimeFormatter),
                            article.getUser().getNickName()
                    )
            );
        }
        return new PageImpl<>(articleSummaryList, pageable, articles.getTotalElements());
    }

    @Transactional
    public List<Article> getRecentArticleList() {
        return articleRepository.findTop5OrderByCreatedAtDesc();
    }

    @Transactional

    public CreateArticleResDto createArticle(CreateArticleReqDto reqDto) {
        User loginUser = userService.getLoginUser();
        Article article = new Article(
                reqDto.getTitle(),
                reqDto.getContent(),
                loginUser
        );
        articleRepository.save(article);
        return new CreateArticleResDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt(),
                article.getUser().getNickName()
        );
    }

    @Transactional
    public GetArticleDetailResDto getArticleDetail(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));

        List<Comment> parentComments = commentRepository.findParentsByArticleId(id);
        // 부모 댓글들과 그 자식 댓글들을 모두 포함하는 목록
        List<CommentDetailDto> comments = new ArrayList<>();

        for (Comment parentComment : parentComments) {
            // 부모 댓글의 상세 내역 조회
            CommentDetailDto parentCommentDto = new CommentDetailDto(parentComment);

            // 부모 댓글의 자식 댓글 조회
            List<Comment> replies = commentRepository.findRepliesByParentId(parentComment.getId());
            List<CommentDetailDto> replyDtos = new ArrayList<>();

            for (Comment reply : replies) {
                replyDtos.add(new CommentDetailDto(reply));
            }

            parentCommentDto.setReplies(replyDtos);
            comments.add(parentCommentDto);
        }
        return new GetArticleDetailResDto(article, comments);
    }

    @Transactional
    public UpdateArticleResDto updateArticle(Long id, UpdateArticleReqDto reqDto) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));
        String loginUserId = userService.getLoginUserInfo().getUserId();
        String authorUserId = article.getUser().getUserId();
        if (!Objects.equals(loginUserId, authorUserId)) {
            throw new AccessDeniedException("no permission to update article");
        }
        article.update(reqDto.getTitle(), reqDto.getContent());
        articleRepository.save(article);
        return new UpdateArticleResDto(article);
    }

    @Transactional
    public void deleteArticle(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article Not Found"));
        String loginUserId = userService.getLoginUserInfo().getUserId();
        String authorUserId = article.getUser().getUserId();
        if (!Objects.equals(loginUserId, authorUserId)) {
            throw new AccessDeniedException("no permission to delete article");
        }
        articleRepository.deleteById(id);
    }
}
