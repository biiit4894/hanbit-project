package com.example.board.user.controller;

import com.example.board.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserViewController {
    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        return "user/mypage";
    }

    @GetMapping("/signout")
    public String signout(Model model) {
        model.addAttribute("loginUserInfo", userService.getLoginUserInfo());
        return "user/signout";
    }
}
