package com.example.airplaneletter.authentication;

import com.example.airplaneletter.authentication.token.JwtEncoder;
import com.example.airplaneletter.errorCode.ErrorCode;
import com.example.airplaneletter.exception.UnauthorizedException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class AuthenticationExtractor {
    private static final String TOKEN_COOKIE_NAME = "AccessToken";

    public static String extractTokenFromRequest(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (TOKEN_COOKIE_NAME.equals(cookie.getName())) {
                    return JwtEncoder.decodeJwtBearerToken(cookie.getValue());
                }
            }
        }
        throw new UnauthorizedException(ErrorCode.INVALID_TOKEN, "로그인 여부를 확인해주세요.");
    }
}

