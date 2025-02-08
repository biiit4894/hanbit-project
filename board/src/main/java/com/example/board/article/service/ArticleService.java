package com.example.board.article.service;

import com.example.board.article.model.dto.*;
import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

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
                            article.getCreatedAt(),
                            null
                    )
            );
        }
        return new PageImpl<>(articleSummaryList, pageable, articles.getTotalElements());
    }

    @Transactional
    public CreateArticleResDto createArticle(CreateArticleReqDto reqDto) {
        Article article = new Article(
                reqDto.getTitle(),
                reqDto.getContent()
        );
        articleRepository.save(article);
        return new CreateArticleResDto(
                article.getId(),
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt()
        );
    }

    @Transactional
    public GetArticleDetailResDto getArticleDetail(Long id) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));
        return new GetArticleDetailResDto(article);
    }

    @Transactional
    public UpdateArticleResDto updateArticle(Long id, UpdateArticleReqDto reqDto) {
        Article article = articleRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Article not found"));
        article.update(reqDto.getTitle(), reqDto.getContent());
        articleRepository.save(article);
        return new UpdateArticleResDto(article);
    }

    // TODO: 게시글 삭제 api 완성할 것
    @Transactional
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
