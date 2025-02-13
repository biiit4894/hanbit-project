package com.example.board.article.repository;

import com.example.board.article.model.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);

    @Query("select a from Article a order by a.createdAt desc limit 6")
    List<Article> findTop6OrderByCreatedAtDesc();
}
