package com.example.board.user.service;

import com.example.board.user.model.dto.*;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;
import com.example.board.util.JwtTokenInfoDto;
import com.example.board.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

//    @Value("spring.refresh-expire-time")
//    private long refreshExpireTime;

    public SignupResDto signup(SignupReqDto reqDto) {
        User user = new User(
                reqDto.getUserId(),
                encoder.encode(reqDto.getPassword()),
                reqDto.getUserName(),
                reqDto.getEmail()
        );
        userRepository.save(user);
        return new SignupResDto(
                user.getUserId(),
                user.getUserName(),
                user.getEmail());
    }

    public LoginResDto login(LoginReqDto reqDto) {
        User user = userRepository.findByUserId(reqDto.getUserId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        log.info("password: {}", user.getPassword());
//        if (!encoder.matches(reqDto.getPassword(), user.get().getPassword())) {
//            throw new UsernameNotFoundException("Invalid username or password");
//        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(reqDto.getUserId(), reqDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("Authentication 처리 완료");
        JwtTokenInfoDto createToken = jwtTokenProvider.createToken(authentication);

        return new LoginResDto(
                user.getUserId(),
                createToken.getGrantType(),
                createToken.getAccessToken(),
                createToken.getRefreshToken()
        );
    }


}
