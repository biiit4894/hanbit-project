package com.example.board.article.controller;

import com.example.board.article.service.ArticleService;
import com.example.board.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "article-view", description = "자유게시판 뷰 API")
    @Operation(
            summary = "대시보드 뷰  API",
            description = "페이지네이션을 이용해 전체 게시글을 조회하는 대시보드 뷰를 위한 API이다."
    )
    @GetMapping("/dashboard")
    public String dashboard(Model model, @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        if (!Objects.equals(model.getAttribute("authPrincipal"), "anonymousUser")) {
            model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        }
        model.addAttribute("pagedArticles", articleService.getArticleList(page));
        return "article/dashboard";
    }

    @Tag(name = "article-view", description = "자유게시판 뷰 API")
    @Operation(
            summary = "게시글 상세 조회 뷰 API",
            description = "개별 게시글을 상세 조회하고 수정할 수 있는 article 뷰를 위한 API이다."
    )
    @GetMapping("/dashboard/{id}")
    public String articleDetail(Model model, @PathVariable("id") Long id) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        if (!Objects.equals(model.getAttribute("authPrincipal"), "anonymousUser")) {
            model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        }
        model.addAttribute("articleDetail", articleService.getArticleDetail(id));
        return "article/article";
    }

    @Tag(name = "article-view", description = "자유게시판 뷰 API")
    @Operation(
            summary = "게시글 작성 뷰 API",
            description = "게시글을 작성하는 editor 뷰를 위한 API이다."
    )
    @GetMapping("/editor")
    public String articleEditor() {
        return "article/editor";
    }

}
