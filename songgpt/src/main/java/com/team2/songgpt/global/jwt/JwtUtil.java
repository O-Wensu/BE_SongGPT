package com.team2.songgpt.global.jwt;

import com.team2.songgpt.dto.member.TokenDto;
import com.team2.songgpt.entity.RefreshToken;
import com.team2.songgpt.global.security.UserDetailsServiceImpl;
import com.team2.songgpt.repository.RefreshTokenRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
@Getter
public class JwtUtil {

    public static final String ACCESS_TOKEN = "Access_Token";
    public static final String REFRESH_TOKEN = "Refresh_Token";
    private static final long ACCESS_TIME = 60 * 60 * 1000L; //1시간
    private static final long REFRESH_TIME = (60 * 1000L) * 60 * 24 * 7; //7일
    private static final String BEARER_PREFIX = "Bearer ";
    private final UserDetailsServiceImpl userDetailsService;
    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${jwt.secret.key}")
    private String SECURITY_KEY;
    private Key key;
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; //HS256 암호화 알고리즘 사용

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(SECURITY_KEY);
        key = Keys.hmacShaKeyFor(bytes);
    }

    // 토큰 생성
    public TokenDto createAllToken(String email) {
        return new TokenDto(createToken(email, ACCESS_TOKEN),
                createToken(email, REFRESH_TOKEN));
    }

    public String createToken(String email, String type) {
        Date date = new Date();
        long time = type.equals(ACCESS_TOKEN) ? ACCESS_TIME : REFRESH_TIME;

        //토큰 앞은 Bearer이 붙음
        //String 형식의 jwt토큰으로 반환됨
        return BEARER_PREFIX +
                Jwts.builder()
                        .signWith(key,signatureAlgorithm) //생성한 key 객체와 key객체를 어떤 알고리즘을 통해 암호화 할건지 지정
                        .setSubject(email) //subject라는 키에 username 넣음
                        .setExpiration(new Date(date.getTime() + time))
                        .setIssuedAt(date) //언제 토큰이 생성 되었는가
                        .compact();
    }

    public String createExpiredToken(String email, String type, Date expiredDate) {
        Date date = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .signWith(key,signatureAlgorithm) //생성한 key 객체와 key객체를 어떤 알고리즘을 통해 암호화 할건지 지정
                        .setSubject(email) //subject라는 키에 username 넣음
                        .setExpiration(expiredDate)
                        .setIssuedAt(date) //언제 토큰이 생성 되었는가
                        .compact();
    }

    // header 토큰을 가져오기
    public String resolveToken(HttpServletRequest request, String type) {
        if (type.equals(ACCESS_TOKEN)) {
            String bearerToken = request.getHeader(ACCESS_TOKEN);
            //토큰 값이 있는지, 토큰 값이 Bearer 로 시작하는지 판단
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
                //Bearer를 자른 값을 전달
                return bearerToken.substring(7);
            }
            return null;
        } else {
            String bearerToken = request.getHeader(REFRESH_TOKEN);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(7);
            }
            return null;
        }
    }

    // 토큰 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }
        catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // RefreshToken 검증 : DB에 저장돼 있는 토큰과 비교
    public Boolean refreshTokenValidation(String token) {
        if(!validateToken(token)) return false;

        Optional<RefreshToken> refreshToken = refreshTokenRepository.findByEmail(getUserInfoFromToken(token));
                return refreshToken.isPresent() && token.equals(refreshToken.get().getRefreshToken().substring(7));
    }

    // 토큰에서 사용자 정보 가져오기
    public String getUserInfoFromToken(String token) {
        // token 을 키를 사용해 복호화
        //검증 하고, token 의 payload 에서 getBody().getSubject() 를 통해서 안에 들어있는 정보를 가져옴
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();
    }

    // 인증 객체 생성
    public Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }

    // AccessToken 헤더 설정
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader(ACCESS_TOKEN, accessToken);
    }

    // RefreshToken 헤더 설정
    public void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader(REFRESH_TOKEN, refreshToken);
    }
}