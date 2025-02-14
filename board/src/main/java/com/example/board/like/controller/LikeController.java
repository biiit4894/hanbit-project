package com.example.board.like.controller;

import com.example.board.like.model.dto.CreateArticleLikeResDto;
import com.example.board.like.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like")
public class LikeController {
    private final LikeService likeService;

    @Tag(name = "like", description = "좋아요 API")
    @Operation(
            summary = "좋아요 누르기 API",
            description = "게시글에 좋아요를 누르기 위한 API로, 게시글 id를 query string으로 전달한다."
    )
    @PostMapping("")
    public ResponseEntity<CreateArticleLikeResDto> createLikeByArticleId(@RequestParam Long articleId) {
        return new ResponseEntity<>(likeService.createLikeByArticleId(articleId), HttpStatus.CREATED);
    }

    @Tag(name = "like", description = "좋아요 API")
    @Operation(
            summary = "좋아요 취소하기 API",
            description = "게시글에 누른 좋아요를 취소하기 위한 API로, 게시글 id를 query string으로 전달한다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLikeById(@PathVariable Long id) {
        likeService.deleteLikeById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
