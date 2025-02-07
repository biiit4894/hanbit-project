package com.example.board.article.controller;

import com.example.board.article.model.dto.*;
import com.example.board.article.service.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<List<ArticleSummaryDto>> getArticleList() {
        List<ArticleSummaryDto> articles = articleService.getArticleList();
        return new ResponseEntity<>(articles, HttpStatus.OK);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
