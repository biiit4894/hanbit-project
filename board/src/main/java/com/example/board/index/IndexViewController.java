package com.example.board.index;

import com.example.board.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class IndexViewController {
    private final UserService userService;

    @GetMapping("/")
    public String index(Model model) {

        model.addAttribute("authPrincipal", userService.getAuthenticationPrincipal());
        return "index";
    }
}
