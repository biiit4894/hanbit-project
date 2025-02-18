package com.example.board.user.controller;

import com.example.board.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserViewController {
    private final UserService userService;

    @Tag(name = "user-view", description = "유저 뷰 API")
    @GetMapping("/login")
    public String login(
            Model model,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "exception", required = false) String exception
    ) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        return "user/login";
    }

    @Tag(name = "user", description = "유저 API")
    @GetMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/";
    }

    @Tag(name = "user-view", description = "유저 뷰 API")
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        return "user/signup";
    }

    @Tag(name = "user-view", description = "유저 뷰 API")
    @GetMapping("/mypage")
    public String mypage(Model model) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        return "user/mypage";
    }

    @Tag(name = "user-view", description = "유저 뷰 API")
    @GetMapping("/signout")
    public String signout(Model model) {
        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        return "user/signout";
    }
}
