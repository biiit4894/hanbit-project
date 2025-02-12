package com.example.board.article.controller;

import com.example.board.article.service.ArticleService;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleViewController {
    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(required = false, defaultValue="0") int page) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        model.addAttribute("pagedArticles", articleService.getArticleList(page));
        return "article/dashboard";
    }
}
