package com.example.board.like.repository;

import com.example.board.like.model.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> findAllByArticleId(Long articleId);
}
