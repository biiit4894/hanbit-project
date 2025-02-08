package com.example.board.like.service;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.like.model.dto.CreateArticleLikeResDto;
import com.example.board.like.model.entity.Like;
import com.example.board.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;
    @Transactional
    public CreateArticleLikeResDto createLikeByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("Article Not Found"));

        Like like = new Like(article);
        likeRepository.save(like);

        article.increaseLikeCount();

        return new CreateArticleLikeResDto(
                like.getId(),
                like.getCreatedAt(),
                like.getArticle().getId()
        );
    }
}
