package com.example.board.article.controller;

import com.example.board.article.model.dto.*;
import com.example.board.article.service.ArticleService;
import com.example.board.exception.CustomValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    @Tag(name = "article", description = "게시글 API")
    @Operation(
            summary = "전체 게시글 목록 조회 API",
            description = "전체 게시글을 간략히 모아 조회하는 API이며, 페이징을 포함한다."
    )
    @GetMapping("")
    public ResponseEntity<Page<ArticleSummaryDto>> getArticleList(@RequestParam(defaultValue="0")int page) {
        return new ResponseEntity<>(articleService.getArticleList(page), HttpStatus.OK);
    }

    @Tag(name = "article", description = "게시글 API")
    @PostMapping("")
    @Operation(
            summary = "게시글 작성 API",
            description = "자유게시판 게시글을 생성하는 API로, 글자 수 제한이 있는 제목과 내용을 request body로 전달한다."
    )
    public ResponseEntity<CreateArticleResDto> createArticle(@Valid @RequestBody CreateArticleReqDto createArticleReqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);

        }
        return new ResponseEntity<>(articleService.createArticle(createArticleReqDto), HttpStatus.CREATED);
    }

    @Tag(name = "article", description = "게시글 API")
    @GetMapping("/{id}")
    @Operation(
            summary = "게시글 상세 조회 API",
            description = "개별 게시글을 내용, 댓글 목록까지 세부적으로 조회하는 API이다. query string으로 게시글 id를 전달해야 한다."
    )
    public ResponseEntity<GetArticleDetailResDto> getArticleDetail(@PathVariable Long id) {
        return new ResponseEntity<>(articleService.getArticleDetail(id), HttpStatus.OK);
    }

    @Tag(name = "article", description = "게시글 API")
    @Operation(
            summary = "게시글 수정 API",
            description = "게시글을 수정하는 API로, query string으로 수정할 게시글의 id를 지정하며, 글자 수 제한이 있는 제목과 내용을 request body에 전달해야 한다."
    )
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

    @Tag(name = "article", description = "게시글 API")
    @Operation(
            summary = "게시글 삭제 API",
            description = "게시글을 삭제하는 API로, query string으로 삭제할 게시글의 id를 전달해야 한다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
