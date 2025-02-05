package com.example.board.article.service;

import com.example.board.article.model.dto.CreateArticleReqDto;
import com.example.board.article.model.dto.CreateArticleResDto;
import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public CreateArticleResDto createArticle(CreateArticleReqDto reqDto) {
        Article article = new Article(
                reqDto.getTitle(),
                reqDto.getContent()
        );
        articleRepository.save(article);
        return new CreateArticleResDto(
                article.getTitle(),
                article.getContent(),
                article.getCreatedAt()
        );
    }

}
