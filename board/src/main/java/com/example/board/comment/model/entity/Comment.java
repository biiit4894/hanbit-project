package com.example.board.comment.model.entity;

import com.example.board.article.model.entity.Article;
import com.example.board.user.model.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    @NotBlank
    @Size(max = 500)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Column(nullable = false)
    private Boolean isDeleted;

    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Comment(String content, Article article, Comment parent, User user) {
        this.content = content;
        this.createdAt = LocalDateTime.now();
        this.isDeleted = false;
        this.article = article;
        this.parent = parent;
        this.user = user;
    }

    public void updateContent(String content) {
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void markDeleted() {
        this.isDeleted = true;
    }

    public void addReply(Comment reply) {
        replies.add(reply);
        reply.setParent(this);
    }

    public void removeReply(Comment reply) {
        replies.remove(reply);
        reply.setParent(null);
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    private void setParent(Comment parent) {
        this.parent = parent;
    }
}
