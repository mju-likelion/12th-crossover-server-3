package com.example.airplaneletter.authentication;

import com.example.airplaneletter.authentication.token.JwtEncoder;
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
        throw new RuntimeException();
    }
}

