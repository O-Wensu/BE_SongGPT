package com.team2.songgpt.validator;

import com.team2.songgpt.global.exception.ExceptionMessage;
import com.team2.songgpt.global.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final JwtUtil jwtUtil;

    public void tokenNullCheck(String token) {
        if (token == null) {
            throw new NullPointerException(ExceptionMessage.HAS_NO_TOKEN.getMessage());
        }
    }

    public void tokenValidateCheck(String token) {
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException(ExceptionMessage.EXPIRED_TOKEN.getMessage());
        }
    }
}
