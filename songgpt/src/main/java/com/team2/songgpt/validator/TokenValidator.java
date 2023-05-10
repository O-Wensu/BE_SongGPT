package com.team2.songgpt.validator;

import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.exception.ExceptionMessage;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidator {
    private final JwtUtil jwtUtil;
    private final MemberRepository memberRepository;

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

    public Member findMemberByToken(String token) {
        String userInfo = jwtUtil.getUserInfoFromToken(token);
        return memberRepository.findByEmail(userInfo).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.EXPIRED_TOKEN.getMessage())
        );
    }
}
