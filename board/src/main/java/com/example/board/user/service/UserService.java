package com.example.board.user.service;

import com.example.board.user.model.dto.SignupReqDto;
import com.example.board.user.model.dto.SignupResDto;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

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
}
