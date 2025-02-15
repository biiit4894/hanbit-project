package com.example.board.index;

import com.example.board.article.service.ArticleService;
import com.example.board.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "index-view", description = "인덱스 뷰 API")
    @Operation(
            summary = "인덱스 페이지 뷰 API",
            description = "가장 최근에 작성된 6개 게시글을 모아 조회하는 인덱스 페이지를 위한 API이다."
    )
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
