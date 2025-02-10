package com.example.board.util;

import com.example.board.article.model.entity.Article;
import com.example.board.article.repository.ArticleRepository;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public void run(String[] args) {
        if (userRepository.count() == 0) {
            List<User> testUsers = new ArrayList<>();

            User user1 = new User(
                    "test1",
                    encoder.encode("test1"),
                    "테스트유저1",
                    "test1@test1.com"
            );

            User user2 = new User(
                    "test2",
                    encoder.encode("test2"),
                    "테스트유저2",
                    "test2@test2.com"
            );

            testUsers.add(user1);
            testUsers.add(user2);

            userRepository.saveAll(testUsers);

            List<Article> articles = new ArrayList<>();
            for (int i = 1; i <= 5; i++) {
                articles.add(new Article("제목 " + i, "내용입니다. 내용입니다. 내용입니다. ", user1));
                articles.add(new Article("제목 " + i, "내용입니다. 내용입니다. 내용입니다. ", user2));
            }

            articleRepository.saveAll(articles);
        }


    }
}
