package com.example.board.article.service;

import com.example.board.article.model.dto.*;
import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

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
}
