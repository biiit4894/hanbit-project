package com.example.board.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length=20, unique = true)
    @NotBlank
    @Size(max=20)
    private String userId;

    @Column(nullable = false, length=20)
    @NotBlank
    @Size(max=20)
    private String password;

    @Column(nullable = false, length=20, unique = true)
    @NotBlank
    @Size(max=20)
    private String userName;

    @Column(nullable = false, length=20, unique = true)
    @NotBlank
    private String email;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime deletedAt;

    @Builder
    public User(String userId, String password, String userName, String email) {
        this.userId = userId;
        this.password = password;
        this.userName = userName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }
}
