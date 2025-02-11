package com.example.board.user.controller;

import com.example.board.exception.CustomValidationException;
import com.example.board.user.model.dto.*;
import com.example.board.user.service.UserService;
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

    @PostMapping("/signup")
    public ResponseEntity<SignupResDto> signup(@Valid @RequestBody SignupReqDto reqDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
                log.info("signup error defaultMessage: {}", error.getDefaultMessage());
            }
            throw new CustomValidationException("유효성 검사 실패", errorMap);
        } else {
            return new ResponseEntity<>(userService.createUser(reqDto), HttpStatus.CREATED);
        }
    }

    @PostMapping("/signout")
    public ResponseEntity<SignoutResDto> signout(@RequestBody SignoutReqDto reqDto) {
        return new ResponseEntity<>(userService.setUserDeletedAt(reqDto),HttpStatus.OK);
    }
}
