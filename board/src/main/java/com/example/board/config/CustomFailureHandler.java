package com.example.board.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String message;
        if (exception instanceof BadCredentialsException || exception instanceof UsernameNotFoundException) {
            message = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "내부적으로 발생한 시스템 문제로 인해 요청을 처리할 수 없습니다.";
        } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
            message = "인증 요청이 거부되었습니다";
        } else {
            message = "알 수 없는 이유로 로그인에 실패하였습니다.";
        }

        message = URLEncoder.encode(message, StandardCharsets.UTF_8);
        setDefaultFailureUrl("/login?error=true&exception=" + message);
        super.onAuthenticationFailure(request, response, exception);
    }
}
