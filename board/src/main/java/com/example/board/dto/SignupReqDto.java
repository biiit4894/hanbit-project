package com.example.board.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupReqDto {
    private String userId;
    private String password;
    private String userName;
    private String email;
}
