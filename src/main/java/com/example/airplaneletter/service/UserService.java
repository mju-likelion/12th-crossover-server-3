package com.example.airplaneletter.service;

import com.example.airplaneletter.authentication.PasswordHashEncryption;
import com.example.airplaneletter.authentication.token.JwtEncoder;
import com.example.airplaneletter.authentication.token.JwtTokenProvider;
import com.example.airplaneletter.dto.user.CreateUserDto;
import com.example.airplaneletter.dto.login.LoginDto;
import com.example.airplaneletter.dto.term.TermDto;
import com.example.airplaneletter.errorCode.ErrorCode;
import com.example.airplaneletter.exception.ConflictException;
import com.example.airplaneletter.exception.NotFoundException;
import com.example.airplaneletter.exception.UnauthorizedException;
import com.example.airplaneletter.model.Term;
import com.example.airplaneletter.model.TermUser;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.TermRepository;
import com.example.airplaneletter.repository.TermUserRepository;
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
    private TermRepository termRepository;
    private TermUserRepository termUserRepository;


    // 회원 가입
    public void createUser(CreateUserDto createUserDto) {
        // 이메일 중복 검사
        this.isEmailExist(createUserDto.getEmail());

        String encryptedPassword = this.passwordHashEncryption.encrypt(createUserDto.getPassword());

        User user = User.builder()
                .email(createUserDto.getEmail())
                .nickname(createUserDto.getNickname())
                .password(encryptedPassword)
                .build();
        // 약관 동의
        for (TermDto termDto : createUserDto.getAgreements()) {
            Term agreed = termRepository.findTermById(termDto.getTermId());
            if(termDto.isAgreed() == false){
                throw new NotFoundException(ErrorCode.MISSING_TERMS, "약관에 동의해 주세요");
            }
            TermUser termUser = TermUser.builder()
                    .term(agreed)
                    .user(user)
                    .agreed(termDto.isAgreed())
                    .build();

            termUserRepository.save(termUser);
        }

        userRepository.save(user);
    }

    // 이메일 중복 검사
    public void isEmailExist(String email) {
        User user = this.userRepository.findByEmail(email);
        if (user != null) {
            throw new ConflictException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    // 로그인
    public void login(LoginDto loginDto, HttpServletResponse response) {

        User user = this.userRepository.findByEmail(loginDto.getEmail());
        if(user == null) {
            throw new NotFoundException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordHashEncryption.matches(loginDto.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(ErrorCode.UNAUTHORIZED_USER);
        }

        String payload = user.getId().toString();
        String accessToken = jwtTokenProvider.createToken(payload);
        ResponseCookie cookie = ResponseCookie.from("AccessToken", JwtEncoder.encodeJwtBearerToken(accessToken))
                .maxAge(Duration.ofMillis(1800000))
                .httpOnly(true)
                .sameSite("None").secure(true)
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
