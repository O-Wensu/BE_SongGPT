package com.team2.songgpt.validator;

import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.exception.ExceptionMessage;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;



    public void validateMemberByEmail(String email) {
        memberRepository.findByEmail(email).ifPresent(member -> {
            throw new IllegalArgumentException(ExceptionMessage.DUPLICATED_MEMBER.getMessage());
        });
    }

    public void validateMemberByNickname(String nickname) {
        memberRepository.findByNickname(nickname).ifPresent(member -> {
            throw new IllegalArgumentException(ExceptionMessage.DUPLICATED_NICKNAME.getMessage());
        });
    }

    public Member validateMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException(ExceptionMessage.NO_EXIST_MEMBER.getMessage())
        );
    }

    public void validatePassword(String password, Member member) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException(ExceptionMessage.NO_MATCH_PASSWORD.getMessage());
        }
    }
}
