package com.example.airplaneletter.dto.user;

import com.example.airplaneletter.dto.term.AgreeToTermDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
public class CreateUserDto {
    @NotBlank(message = "닉네임이 비어있습니다.")
    @Length(min = 2, max = 9, message = "닉네임은 2자 이상 9자 이하여야 합니다.")
    @Pattern(regexp = "^[가-힣]*$", message = "닉네임은 한글만 입력할 수 있습니다.")
    private String nickname;

    @NotBlank(message = "이메일이 비어있습니다.")
    @Pattern(regexp = "^[A-Za-z0-9]+@[A-Za-z0-9]+.[A-Za-z]{2,6}$",
            message = "이메일 형식이 맞지 않습니다.")
    private String email;

    @NotBlank(message = "비밀번호가 비어있습니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,13}", message = "비밀번호는 영문과 숫자, 특수기호를 사용해야합니다.")
    @Length(min = 8, max = 13, message = "비밀번호는 8자 이상 13자 이하여야 합니다.")
    private String password;

    private List<AgreeToTermDto> agreements;
}
