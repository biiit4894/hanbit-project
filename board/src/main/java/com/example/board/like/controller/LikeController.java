package com.example.board.like.controller;

import com.example.board.like.model.dto.CreateArticleLikeResDto;
import com.example.board.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;

    @PostMapping("")
    public ResponseEntity<CreateArticleLikeResDto> createLikeForArticle(@RequestParam Long articleId) {
        return new ResponseEntity<>(likeService.createLikeByArticleId(articleId), HttpStatus.CREATED);
    }
}
