package com.team2.songgpt.service;

import com.team2.songgpt.dto.member.LoginRequestDto;
import com.team2.songgpt.dto.member.LoginResponseDto;
import com.team2.songgpt.dto.member.MemberResponseDto;
import com.team2.songgpt.dto.member.SignupRequestDto;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public MemberResponseDto getMember(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        String userInfo;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                userInfo = jwtUtil.getUserInfoFromToken(token);
                Member member = memberRepository.findByEmail(userInfo).orElseThrow(
                        () -> new IllegalArgumentException("토큰이 유효하지 않습니다.")
                );
                return new MemberResponseDto(member);
            } else {
                throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
            }
        }
        throw new IllegalArgumentException("토큰이 없습니다.");
    }

    @Transactional
    public void signup(@RequestBody SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        String nickname = signupRequestDto.getNickname();

        password = passwordEncoder.encode(password);

        Optional<Member> findEmail = memberRepository.findByEmail(email);
        if(findEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 email입니다.");
        }

        Optional<Member> findNickname = memberRepository.findByNickname(nickname);
        if(findNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        }

        Member member = new Member(email, password, nickname);
        memberRepository.save(member);
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        Member member = memberRepository.findByEmail(email).orElseThrow(
        () -> new IllegalArgumentException("잘못된 email입니다.")
        );

        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        response.addHeader(JwtUtil.ACCESS_HEADER, jwtUtil.createToken(member.getEmail()));
        return new LoginResponseDto(member);
    }
}