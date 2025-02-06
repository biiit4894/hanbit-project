package com.example.board.article.controller;

import com.example.board.article.model.dto.*;
import com.example.board.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<GetArticleDetailResDto> getArticleDetail(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.getArticleDetail(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateArticleResDto> updateArticle(@PathVariable Long id, @RequestBody UpdateArticleReqDto updateArticleReqDto) {
        return new ResponseEntity<>(articleService.updateArticle(id, updateArticleReqDto), HttpStatus.OK);
    }
}
