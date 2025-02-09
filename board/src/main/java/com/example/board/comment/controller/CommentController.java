package com.example.board.comment.controller;

import com.example.board.comment.model.CreateCommentReqDto;
import com.example.board.comment.model.CreateCommentResDto;
import com.example.board.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CreateCommentResDto> createComment(@RequestBody CreateCommentReqDto reqDto) {
        return new ResponseEntity<>(commentService.createComment(reqDto), HttpStatus.CREATED);
    }
}
