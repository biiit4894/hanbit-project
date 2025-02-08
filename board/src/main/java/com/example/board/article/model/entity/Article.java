package com.example.board.article.model.entity;

import com.example.board.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=30)
    @NotBlank
    @Size(max = 30)
    private String title;

    @Column(nullable=false, length=1000)
    @NotBlank
    @Size(max = 1000)
    private String content;

    @Column(nullable=false)
    private Long commentCount;

    @Column(nullable=false)
    private Long likeCount;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    private User user;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
        this.commentCount = 0L;
        this.likeCount = 0L;
        this.createdAt = LocalDateTime.now();
        this.user = null; // TODO: 로그인 유저(작성자) 반영하기
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        this.likeCount--;
    }
}
