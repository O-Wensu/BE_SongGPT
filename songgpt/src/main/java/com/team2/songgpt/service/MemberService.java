package com.team2.songgpt.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team2.songgpt.dto.member.*;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.entity.RefreshToken;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.exception.ExceptionMessage;
import com.team2.songgpt.global.jwt.JwtUtil;
import com.team2.songgpt.repository.MemberRepository;
import com.team2.songgpt.repository.RefreshTokenRepository;
import com.team2.songgpt.validator.MemberValidator;
import com.team2.songgpt.validator.TokenValidator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    private final AmazonS3 amazonS3;
    private final MemberValidator memberValidator;
    private final TokenValidator tokenValidator;

    /**
     * 회원 정보
     */
    @Transactional
    public ResponseDto<MemberResponseDto> getMember(HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);

        //유효성 검사
        tokenValidator.tokenNullCheck(token);
        tokenValidator.tokenValidateCheck(token);
        Member member = memberValidator.findMemberByToken(token);

        MemberResponseDto memberResponseDto = new MemberResponseDto(member);
        return ResponseDto.setSuccess(memberResponseDto);
    }

    /**
     * 회원가입
     */
    @Transactional
    public ResponseDto<?> signup(@RequestBody SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String password = signupRequestDto.getPassword();
        String nickname = signupRequestDto.getNickname();

        password = passwordEncoder.encode(password);

        //유효성 검사
        memberValidator.validateMemberByEmail(email);
        memberValidator.validateMemberByNickname(nickname);

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

        //유효성 검사
        Member member = memberValidator.validateMember(email);
        memberValidator.validatePassword(password, member);

        TokenDto tokenDto = jwtUtil.createAllToken(member.getEmail());
        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(member.getEmail());

        if (refreshToken.isPresent()) {
            refreshTokenRepository.save(refreshToken.get().updateToken(tokenDto.getRefreshToken()));
        } else {
            RefreshToken newToken = new RefreshToken(tokenDto.getRefreshToken(), email);
            refreshTokenRepository.save(newToken);
        }

        setHeader(response, tokenDto);
        LoginResponseDto loginResponseDto = new LoginResponseDto(member);
        return ResponseDto.setSuccess(loginResponseDto);
    }

    /**
     * 로그아웃
     */
    @Transactional
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        String token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);

        tokenValidator.tokenNullCheck(token);
        tokenValidator.tokenValidateCheck(token);

        String userInfo = jwtUtil.getUserInfoFromToken(token);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && userInfo.equals(authentication.getName())) {
            //만료시간이 현재 시간 이전으로 설정된 accessToken을 만들어서 클라이언트에 보냄
            Date now = new Date();
            Date expiredDate = new Date(now.getTime() - 1000);
            String newToken = jwtUtil.createExpiredToken(userInfo, JwtUtil.ACCESS_TOKEN, expiredDate);
            SecurityContextHolder.getContext().setAuthentication(null);
            response.setHeader(JwtUtil.ACCESS_TOKEN, newToken);
            return ResponseDto.setSuccess(null);
        }
        throw new IllegalArgumentException(ExceptionMessage.NOT_LOGIN.getMessage());
    }

    /**
     * 로그아웃
     */
    @Transactional
    public ResponseDto<?> callNewAccessToken(String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        String token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);
        tokenValidator.tokenNullCheck(token);

        boolean isRefreshToken = jwtUtil.refreshTokenValidation(refreshToken);
        if (!isRefreshToken) {
            throw new IllegalArgumentException(ExceptionMessage.EXPIRED_TOKEN.getMessage());
        }

        String email = jwtUtil.getUserInfoFromToken(refreshToken);
        String newAccessToken = jwtUtil.createToken(email, JwtUtil.ACCESS_TOKEN);
        jwtUtil.setHeaderAccessToken(response, newAccessToken);

        String newRefreshToken = jwtUtil.createToken(email, JwtUtil.REFRESH_TOKEN);
        newRefreshToken = newRefreshToken.substring(7);

        Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN, newRefreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        response.setHeader(JwtUtil.ACCESS_TOKEN, newAccessToken);
        return ResponseDto.setSuccess(null);
    }

    //응답 헤더에 액세스, 리프레시 토큰 추가
    public void setHeader(HttpServletResponse response, TokenDto tokenDto) {
        response.addHeader(JwtUtil.ACCESS_TOKEN, tokenDto.getAccessToken());
        response.addHeader(JwtUtil.REFRESH_TOKEN, tokenDto.getRefreshToken());
    }

    /**
     * 프로필 이미지 변경
     */
    @Transactional
    public ResponseDto<?> updateProfile(MultipartFile image, Member member) {
        //중복된 이름 방지를 위한 UUID 붙이기
        String fileName = UUID.randomUUID() + "-" + image.getOriginalFilename();

        //메타 데이터 설정
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentType(image.getContentType());
        objMeta.setContentLength(image.getSize());

        try {
            objMeta.setContentLength(image.getInputStream().available());
            //s3에 파일 업로드
            amazonS3.putObject(bucket, fileName, image.getInputStream(), objMeta);
        } catch (IOException e) {
            throw new IllegalArgumentException(ExceptionMessage.FAIL_IMAGE_UPLOAD.getMessage());
        }

        //이미지 url
        String imageUrl = amazonS3.getUrl(bucket, fileName).toString();

        //회원 프로필 이미지 설정
        Member savedMember = memberValidator.validateMember(member.getEmail());
        savedMember.setImageUrl(imageUrl);
        return ResponseDto.setSuccess(imageUrl);
    }
}