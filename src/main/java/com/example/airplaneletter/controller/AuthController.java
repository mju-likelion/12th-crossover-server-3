package com.example.airplaneletter.controller;

import com.example.airplaneletter.dto.CreateUserDto;
import com.example.airplaneletter.dto.LoginDto;
import com.example.airplaneletter.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/auth/signIn")
    public void createUser(@RequestBody CreateUserDto createUserDto) {
        this.userService.createUser(createUserDto);
    }

    // 로그인
    @PostMapping("/auth/login")
    public void login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        this.userService.login(loginDto, response);
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public void logout(HttpServletResponse response) {
        this.userService.logout(response);
    }
}
