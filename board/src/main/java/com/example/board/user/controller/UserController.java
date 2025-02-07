package com.example.board.user.controller;

import com.example.board.user.model.dto.*;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResDto> signup(@RequestBody SignupReqDto reqDto) {
        return new ResponseEntity<>(userService.signup(reqDto), HttpStatus.CREATED);
    }
}
