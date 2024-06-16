package com.example.airplaneletter.controller;

import com.example.airplaneletter.model.User;
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
    public void createEmployee(@RequestBody User user) {
        this.userService.createUser(user);
    }

    // 로그인
    @PostMapping("/auth/login")
    public void login(@RequestBody String email, @RequestBody String password, HttpServletResponse response) {
        this.userService.login(email, password, response);
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public void logout(HttpServletResponse response) {
        this.userService.logout(response);
    }
}
