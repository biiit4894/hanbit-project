package com.example.board.service;

import com.example.board.dto.SignupReqDto;
import com.example.board.entity.User;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    public void signup(SignupReqDto reqDto) {
        User user = new User(
                reqDto.getUserId(),
                reqDto.getPassword(),
                reqDto.getUserName(),
                reqDto.getEmail()
        );
        userRepository.save(user);
    }
}
