package com.example.board.config;

import com.example.board.filter.JwtAuthenticationFilter;
import com.example.board.util.JwtTokenProvider;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;


@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private JwtTokenProvider jwtTokenProvider;
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    WebSecurityCustomizer configure() {
        return web -> web.ignoring().requestMatchers(toH2Console()).requestMatchers("/static/**");
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity, JwtTokenProvider jwtTokenProvider) throws Exception {
        httpSecurity
                .csrf(auth -> auth
                        .ignoringRequestMatchers("/api/user/signup", "/api/user/login")
                        .ignoringRequestMatchers(PathRequest.toH2Console())
                )
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/user/signup", "/api/user/login").permitAll()
                            .requestMatchers(PathRequest.toH2Console()).permitAll()
                            .anyRequest().authenticated()
                )
//                .addFilterBefore(
//                        new JwtAuthenticationFilter(jwtTokenProvider),
//                        UsernamePasswordAuthenticationFilter.class
//                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));;

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
