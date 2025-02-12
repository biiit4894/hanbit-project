package com.example.board.index;

import com.example.board.article.service.ArticleService;
import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class IndexViewController {
    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        if (!Objects.equals(model.getAttribute("authPrincipal"), "anonymousUser")) {
            model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        }
        model.addAttribute("recentArticles", articleService.getRecentArticleList());
        return "index";
    }
}
