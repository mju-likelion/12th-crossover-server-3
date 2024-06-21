package com.example.airplaneletter.controller;

import com.example.airplaneletter.dto.user.CreateUserDto;
import com.example.airplaneletter.dto.login.LoginDto;
import com.example.airplaneletter.dto.ResponseDto;
import com.example.airplaneletter.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/auth/signIn")
    public ResponseEntity<ResponseDto<Void>> createUser(@Valid @RequestBody CreateUserDto createUserDto) {
        this.userService.createUser(createUserDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "create User"), HttpStatus.OK);
    }

    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<ResponseDto<Void>> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response) {
        this.userService.login(loginDto, response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "login successfully"), HttpStatus.OK);
    }

    // 로그아웃
    @PostMapping("/auth/logout")
    public ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse response) {
        this.userService.logout(response);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "logout successfully"), HttpStatus.OK);
    }
}
