package com.example.board.like.model.entity;

import com.example.board.article.model.entity.Article;
import com.example.board.user.model.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "likes")
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    @ManyToOne
    private Article article;

    public Like(Article article) {
        this.createdAt = LocalDateTime.now();
        this.user = null;
        this.article = article;
    }
}
