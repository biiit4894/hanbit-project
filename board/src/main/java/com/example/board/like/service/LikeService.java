package com.example.board.like.service;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.like.model.dto.CreateArticleLikeResDto;
import com.example.board.like.model.dto.GetLikeDetailResDto;
import com.example.board.like.model.entity.Like;
import com.example.board.like.repository.LikeRepository;
import com.example.board.user.model.entity.User;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final UserService userService;
    private final LikeRepository likeRepository;
    private final ArticleRepository articleRepository;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Transactional
    public CreateArticleLikeResDto createLikeByArticleId(Long articleId) {
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new IllegalArgumentException("Article Not Found"));
        User loginUser = userService.getLoginUser();

        Like like = likeRepository.findByUserIdAndArticleId(loginUser.getId(), articleId);

        if (like != null) {  // 이미 게시글에 좋아요를 누른 경우
            throw new IllegalArgumentException("like already created with id: " + like.getId());
        } else {
            Like newLike = new Like(article, loginUser);
            likeRepository.save(newLike);

            article.increaseLikeCount();

            return new CreateArticleLikeResDto(
                    newLike.getId(),
                    newLike.getCreatedAt(),
                    newLike.getArticle().getId(),
                    newLike.getUser().getNickName()
            );
        }
    }

    @Transactional
    public void deleteLikeById(Long id) {
        Like like = likeRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Like Not Found"));
        likeRepository.deleteById(id);
        Article article = like.getArticle();
        article.decreaseLikeCount();
    }
}
