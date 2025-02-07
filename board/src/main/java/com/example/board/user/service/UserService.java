package com.example.board.user.service;

import com.example.board.user.model.dto.*;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Transactional
    public SignupResDto createUser(SignupReqDto reqDto) {

        User user = new User(
                reqDto.getUserId(),
                encoder.encode(reqDto.getPassword()),
                reqDto.getNickName(),
                reqDto.getEmail()
        );
        userRepository.save(user);
        return new SignupResDto(
                user.getUserId(),
                user.getNickName(),
                user.getEmail()
        );
    }

    @Transactional
    public SignoutResDto setUserDeletedAt(SignoutReqDto reqDto) {
        Long id = getLoginUserInfo().getId();
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        if (!encoder.matches(reqDto.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Incorrect password");
        }
        userRepository.updateDeletedAt(id);
        return new SignoutResDto(user);
    }

    public LoginUserInfoDto getLoginUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return new LoginUserInfoDto(
                user.getId(),
                user.getUserId(),
                user.getNickName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getDeletedAt()
        );
    }

    public static class InvalidPasswordException extends RuntimeException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

}
