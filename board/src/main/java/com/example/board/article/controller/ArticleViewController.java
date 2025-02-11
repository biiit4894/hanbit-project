package com.example.board.article.controller;

import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleViewController {
    private final UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        return "article/dashboard";
    }
}
