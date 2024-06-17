package com.example.airplaneletter.service;

import com.example.airplaneletter.authentication.PasswordHashEncryption;
import com.example.airplaneletter.authentication.token.JwtEncoder;
import com.example.airplaneletter.authentication.token.JwtTokenProvider;
import com.example.airplaneletter.dto.CreateUserDto;
import com.example.airplaneletter.dto.LoginDto;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordHashEncryption passwordHashEncryption;
    private final JwtTokenProvider jwtTokenProvider;

    // 회원 가입
    public void createUser(CreateUserDto createUserDto) {

        String encryptedPassword = this.passwordHashEncryption.encrypt(createUserDto.getPassword());

        User user = User.builder()
                .email(createUserDto.getEmail())
                .nickname(createUserDto.getNickname())
                .password(encryptedPassword)
                .build();
        userRepository.save(user);
    }

    // 로그인
    public void login(LoginDto loginDto, HttpServletResponse response) {

        User user = this.userRepository.findByEmail(loginDto.getEmail());
        if(user == null) {
            throw new RuntimeException("User not found");
        }

        if (!passwordHashEncryption.matches(loginDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Incorrect password");
        }

        String payload = user.getId().toString();
        String accessToken = jwtTokenProvider.createToken(payload);
        ResponseCookie cookie = ResponseCookie.from("AccessToken", JwtEncoder.encodeJwtBearerToken(accessToken))
                .maxAge(Duration.ofMillis(1800000))
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }

    // 로그아웃
    public void logout(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from("AccessToken", null)
                .maxAge(0)
                .path("/")
                .build();
        response.addHeader("Set-Cookie", cookie.toString());
    }
}
