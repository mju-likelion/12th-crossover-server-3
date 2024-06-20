package com.example.airplaneletter.authentication.interceptor;

import com.example.airplaneletter.authentication.AuthenticationContext;
import com.example.airplaneletter.authentication.AuthenticationExtractor;
import com.example.airplaneletter.authentication.token.JwtTokenProvider;
import com.example.airplaneletter.errorCode.ErrorCode;
import com.example.airplaneletter.exception.NotFoundException;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationContext authenticationContext;
    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String accessToken = AuthenticationExtractor.extractTokenFromRequest(request);
        UUID empId = UUID.fromString(jwtTokenProvider.getPayload(accessToken));
        User user = this.userRepository.findById(empId).orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));
        authenticationContext.setPrincipal(user);
        return true;
    }
}
