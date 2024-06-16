package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.LoginDto;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 가입
    public void createUser(User user) {
        userRepository.save(user);
    }

    // 로그인
    public void login(LoginDto loginDto, HttpServletResponse response) {

    }

    // 로그아웃
    public void logout(HttpServletResponse response) {

    }
}
