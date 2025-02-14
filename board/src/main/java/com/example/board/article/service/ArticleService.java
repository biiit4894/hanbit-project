package com.example.board.article.service;

import com.example.board.article.model.dto.*;
import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.comment.model.dto.CommentDetailDto;
import com.example.board.comment.service.CommentService;
import com.example.board.user.model.entity.User;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
    private final UserService userService;
    private final CommentService commentService;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public Page<ArticleSummaryDto> getArticleList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
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
    public List<ArticlePreviewDto> getRecentArticleList() {
        List<ArticlePreviewDto> articlePreviews = new ArrayList<>();
        List<Article> articles = articleRepository.findTop6OrderByCreatedAtDesc();
        for (Article article : articles) {
            articlePreviews.add(
                    new ArticlePreviewDto(
                            article.getId(),
                            article.getTitle(),
                            article.getContent(),
                            article.getCreatedAt().format(dateTimeFormatter),
                            article.getUser().getNickName()
                    )
            );
        }
        return articlePreviews;
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
        articleRepository.flush();
        return new CreateArticleResDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt().format(dateTimeFormatter),
                article.getUser().getNickName()
        );
    }

    @Transactional
    public GetArticleDetailResDto getArticleDetail(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));

        List<CommentDetailDto> comments = commentService.getParentCommentsByArticleId(id);
        return new GetArticleDetailResDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCommentCount(),
                article.getLikeCount(),
                article.getCreatedAt().format(dateTimeFormatter),
                comments,
                article.getUser().getNickName()
        );
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
