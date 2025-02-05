package com.example.board.article.controller;

import com.example.board.article.model.dto.CreateArticleReqDto;
import com.example.board.article.model.dto.CreateArticleResDto;
import com.example.board.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    @PostMapping("")
    public ResponseEntity<CreateArticleResDto> createArticle(@RequestBody CreateArticleReqDto createArticleReqDto) {
        return new ResponseEntity<>(articleService.createArticle(createArticleReqDto), HttpStatus.CREATED);
    }
}
