package com.example.board.comment.controller;

import com.example.board.comment.model.dto.CreateCommentReqDto;
import com.example.board.comment.model.dto.CreateCommentResDto;
import com.example.board.comment.model.dto.UpateCommentReqDto;
import com.example.board.comment.model.dto.UpdateCommentResDto;
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

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCommentResDto> updateComment(@PathVariable Long id, @RequestBody UpateCommentReqDto reqDto) {
        return new ResponseEntity<>(commentService.updateComment(id, reqDto), HttpStatus.OK);
    }
}
