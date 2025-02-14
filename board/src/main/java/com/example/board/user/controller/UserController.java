package com.example.board.user.controller;

import com.example.board.exception.CustomValidationException;
import com.example.board.user.model.dto.*;
import com.example.board.user.service.UserService;
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
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @Tag(name = "user", description = "유저 API")
    @Operation(
            summary = "회원가입 API",
            description = "유저 생성을 위한 API로, 아이디, 비밀번호, 별명, 이메일을 모두 필수값으로 request body에 전달한다."
    )
    @PostMapping("/signup")
    public ResponseEntity<SignupResDto> signup(@Valid @RequestBody SignupReqDto reqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        } else {
            return new ResponseEntity<>(userService.createUser(reqDto), HttpStatus.CREATED);
        }
    }

    @Tag(name = "user", description = "유저 API")
    @Operation(
            summary = "회원탈퇴 API",
            description = "회원탈퇴를 위한 API로, 비밀번호를 request body에 전달한다. 회원탈퇴를 신청한 유저를 데이터베이스에서 삭제하는 대신, 그 탈퇴일시를 기록한다."
    )
    @PostMapping("/signout")
    public ResponseEntity<SignoutResDto> signout(@RequestBody SignoutReqDto reqDto) {
        return new ResponseEntity<>(userService.setUserDeletedAt(reqDto),HttpStatus.OK);
    }
}
