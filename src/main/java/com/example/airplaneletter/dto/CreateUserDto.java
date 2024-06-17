package com.example.airplaneletter.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserDto {
    @NotBlank(message = "닉네임이 비어있습니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2자 이상 10자 이하여야 합니다.")
    private String nickname;

    @NotBlank(message = "이메일이 비어있습니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}$",
            message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 영문과 숫자, 특수기호를 사용해야합니다.")
    @Size(min = 8, max = 13, message = "비밀번호는 8자 이상 13자 이하여야 합니다.")
    private String password;
}
