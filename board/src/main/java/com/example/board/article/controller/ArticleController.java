package com.example.board.article.controller;

import com.example.board.article.model.dto.*;
import com.example.board.article.service.ArticleService;
import com.example.board.exception.CustomValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping("")
    public ResponseEntity<Page<ArticleSummaryDto>> getArticleList(@RequestParam(defaultValue="0")int page) {
        return new ResponseEntity<>(articleService.getArticleList(page), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<CreateArticleResDto> createArticle(@Valid @RequestBody CreateArticleReqDto createArticleReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("create article error defaultMessage: {}", error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);

        }
        return new ResponseEntity<>(articleService.createArticle(createArticleReqDto), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArticleDetailResDto> getArticleDetail(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.getArticleDetail(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateArticleResDto> updateArticle(@PathVariable Long id, @Valid @RequestBody UpdateArticleReqDto updateArticleReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("update article error defaultMessage: {}", error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);

        }
        return new ResponseEntity<>(articleService.updateArticle(id, updateArticleReqDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
