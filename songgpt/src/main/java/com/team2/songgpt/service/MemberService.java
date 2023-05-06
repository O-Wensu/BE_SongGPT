package com.team2.songgpt.service;

import com.team2.songgpt.dto.member.*;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.RefreshToken;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public ResponseDto<MemberResponseDto> getMember(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);

        //토큰 유효성 검사
        validateToken(token);

        String email = jwtUtil.getUserInfoFromToken(token);
        Member member = validateIsMember(email);
        return ResponseDto.setSuccess(new MemberResponseDto(member));
    }

    /**
     * 회원가입
     */
    @Transactional
    public ResponseDto<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        String nickname = signupRequestDto.getNickname();

        //비밀번호 암호화
        password = passwordEncoder.encode(password);

        //회원 존재 여부, 닉네임 중복 여부 확인
        validateExistMember(email);
        validateExistNickname(nickname);

        Member member = new Member(email, password, nickname);
        memberRepository.save(member);
        return ResponseDto.setSuccess(null);
    }

    /**
     * 로그인
     */
    @Transactional
    public ResponseDto<LoginResponseDto> login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String email = loginRequestDto.getEmail();
        String password = loginRequestDto.getPassword();

        //회원 여부, 비밀번호 일치 여부 확인
        Member member = validateIsMember(email);
        validatePassword(password, member);

        TokenDto tokenDto = jwtUtil.createAllToken(member.getEmail());
        saveRefreshToken(email, member, tokenDto);

        setHeader(response, tokenDto);
        return ResponseDto.setSuccess(new LoginResponseDto(member));
    }

    private void saveRefreshToken(String email, Member member, TokenDto tokenDto) {
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(member.getEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), email);
            refreshTokenRepository.save(newToken);
        }
    }

    /**
     * 로그아웃
     */
    @Transactional
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);

        //토큰 유효성 검사
        validateToken(token);

        String userInfo = jwtUtil.getUserInfoFromToken(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        //로그인 여부 확인
        isLogin(userInfo, authentication);

        //만료시간이 현재 시간 이전으로 설정된 accessToken을 만들어서 클라이언트에 보냄
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() - 1000);
        Jwts.builder().setExpiration(expiredDate);
        String newToken = jwtUtil.createExpiredToken(userInfo, JwtUtil.ACCESS_TOKEN, expiredDate);
        SecurityContextHolder.getContext().setAuthentication(null);
        response.setHeader(JwtUtil.ACCESS_TOKEN, newToken);
        return ResponseDto.setSuccess(null);
    }

    //응답 헤더에 액세스, 리프레시 토큰 추가
    public void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    // ==== 유효성 검사 ====

    private void validateExistMember(String email) {
        memberRepository.findByEmail(email).ifPresent(member -> {
            throw new IllegalArgumentException("이미 등록된 회원입니다.");
        });
    }

    private void validateExistNickname(String nickname) {
        memberRepository.findByNickname(nickname).ifPresent(member -> {
            throw new IllegalArgumentException("중복된 닉네임입니다.");
        });
    }

    private Member validateIsMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("등록되지 않은 회원입니다.")
        );
    }

    private void validatePassword(String password, Member member) {
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("토큰이 존재하지 않습니다.");
        }
        if (!jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("토큰이 유효하지 않습니다.");
        }
    }

    private void isLogin(String userInfo, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() || !userInfo.equals(authentication.getName())) {
            throw new IllegalArgumentException("로그인한 회원이 아닙니다.");
        }
    }
}