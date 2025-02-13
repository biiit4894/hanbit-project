package com.example.board.article.controller;

import com.example.board.article.service.ArticleService;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ArticleViewController {
    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(required = false, defaultValue = "0") int page) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        model.addAttribute("pagedArticles", articleService.getArticleList(page));
        return "article/dashboard";
    }

    @GetMapping("/dashboard/{id}")
    public String articleDetail(Model model, @PathVariable Long id) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        if (!Objects.equals(model.getAttribute("authPrincipal"), "anonymousUser")) {
            model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        }
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        return "article/article";
    }

    @GetMapping("/editor")
    public String articleEditor() {
        return "article/editor";
    }

}
