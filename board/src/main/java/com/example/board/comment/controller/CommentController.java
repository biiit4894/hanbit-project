package com.example.board.comment.controller;

import com.example.board.comment.model.dto.CreateCommentReqDto;
import com.example.board.comment.model.dto.CreateCommentResDto;
import com.example.board.comment.model.dto.UpateCommentReqDto;
import com.example.board.comment.model.dto.UpdateCommentResDto;
import com.example.board.comment.service.CommentService;
import com.example.board.exception.CustomValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @Tag(name = "comment", description = "댓글 API")
    @Operation(
            summary = "댓글 생성 API",
            description = "댓글 작성을 위한 API로, 댓글 내용과 함께 댓글을 작성하고자 하는 게시글의 id와 함께 답글을 작성할 경우 부모 댓글의 id를 request body로 전달해야 한다."
    )
    @PostMapping
    public ResponseEntity<CreateCommentResDto> createComment(@Valid @RequestBody CreateCommentReqDto reqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("create comment error defaultMessage: {}", error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        }
        return new ResponseEntity<>(commentService.createComment(reqDto), HttpStatus.CREATED);
    }

    @Tag(name = "comment", description = "댓글 API")
    @Operation(
            summary = "댓글 수정 API",
            description = "개별 댓글을 수정하기 위한 API로, 수정하고자 하는 댓글의 id를 query string으로 전달하고, 수정하고자 하는 내용을 request body로 전달해야 한다."
    )
    @PutMapping("/{id}")
    public ResponseEntity<UpdateCommentResDto> updateComment(@PathVariable Long id, @Valid @RequestBody UpateCommentReqDto reqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("update comment error defaultMessage: {}", error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        }
        return new ResponseEntity<>(commentService.updateComment(id, reqDto), HttpStatus.OK);
    }

    @Tag(name = "comment", description = "댓글 API")
    @Operation(
            summary = "게시글 삭제 API",
            description = "개별 게시글을 삭제하기 위한 API로, 삭제하고자 하는 댓글의 id를 query string으로 전달해야 한다."
            +"답글이 달리지 않은 댓글의 경우 삭제하며, 답글이 달린 부모 댓글의 경우 삭제 여부만을 마킹한다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
