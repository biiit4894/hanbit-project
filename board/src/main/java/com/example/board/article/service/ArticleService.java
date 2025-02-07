package com.example.board.article.service;

import com.example.board.article.model.dto.*;
import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    @Transactional
    public List<ArticleSummaryDto> getArticleList() {
        List<Article> articleList = articleRepository.findAll();
        List<ArticleSummaryDto> articleSummaryList = new ArrayList<>();

        for (Article article : articleList) {
            articleSummaryList.add(new ArticleSummaryDto(article));
        }
        return articleSummaryList;
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

//    @Transactional
//    public void deleteArticle(Long id) {
//        articleRepository.deleteById(id);
//    }
}
