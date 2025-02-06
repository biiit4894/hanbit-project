package com.example.board.user.model.entity;

import com.example.board.article.model.entity.Article;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=20, unique=true)
    @NotBlank
    @Size(max=20)
    private String userId;

    @Column(nullable=false, length=20)
    @NotBlank
    @Size(max=60)
    private String password;

    @Column(nullable=false, length=20, unique=true)
    @NotBlank
    @Size(max=20)
    private String nickName;

    @Column(nullable=false, length=20, unique=true)
    @NotBlank
    private String email;

    @CreatedDate
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @Column(updatable=false)
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy="user")
    private List<Article> articleList;

    @Builder
    public User(String userId, String password, String nickName, String email) {
        this.userId = userId;
        this.password = password;
        this.nickName = nickName;
        this.email = email;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
