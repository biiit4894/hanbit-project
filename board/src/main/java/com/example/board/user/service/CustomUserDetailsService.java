package com.example.board.user.service;

import com.example.board.user.model.dto.CustomUserInfoDto;
import com.example.board.user.model.entity.CustomUserDetails;
import com.example.board.user.model.entity.User;
import com.example.board.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("해당하는 유저가 없습니다."));
        CustomUserInfoDto dto = new CustomUserInfoDto(user.getUserId());
        return new CustomUserDetails(dto);
    }
}
