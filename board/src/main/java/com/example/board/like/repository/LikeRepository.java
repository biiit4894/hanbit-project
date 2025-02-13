package com.example.board.like.repository;

import com.example.board.like.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByArticleId(Long articleId);

    @Query("select l from Like l where l.user.id = :userId and l.article.id = :articleId")
    Like findByUserIdAndArticleId(Long userId, Long articleId);
}
