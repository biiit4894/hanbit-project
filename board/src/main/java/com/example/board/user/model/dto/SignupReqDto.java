package com.example.board.user.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupReqDto {
    @NotBlank
    @Size(min=4, max=10, message = "4자 이상 10자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 대소문자 또는 숫자만을 사용할 수 있습니다.")
    private String userId;

    @NotBlank
    @Size(min=4, max=20, message="4자 이상 20자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "영어 대소문자 또는 숫자만을 사용할 수 있습니다.")
    private String password;

    @NotBlank
    @Size(min=4, max=20, message="4자 이상 20자 이하이어야 합니다.")
    @Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message="영어 대소문자, 한글, 또는 숫자만을 사용할 수 있습니다.")
    private String nickName;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "example@mail.com과 같은 이메일 형식을 준수해야 합니다.")
    private String email;
}
