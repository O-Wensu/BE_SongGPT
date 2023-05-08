package com.team2.songgpt.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.songgpt.global.dto.SecurityExceptionDto;
import com.team2.songgpt.global.entity.StatusCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 헤더의 토큰 가져오기
        String access_token = jwtUtil.resolveToken(request, JwtUtil.ACCESS_TOKEN);
        String refresh_token = jwtUtil.resolveToken(request, JwtUtil.REFRESH_TOKEN);

        if (access_token != null) {
            if (jwtUtil.validateToken(access_token)) {
                setAuthentication(jwtUtil.getUserInfoFromToken(access_token));
            } else if (refresh_token != null) {
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refresh_token);

                if (isRefreshToken) {
                    String email = jwtUtil.getUserInfoFromToken(refresh_token);
                    String newAccessToken = jwtUtil.createToken(email, JwtUtil.ACCESS_TOKEN);
                    jwtUtil.setHeaderAccessToken(response, newAccessToken);

                    // 리프레시토큰도 헤더에 httpOnly 방식으로 저장
                    Cookie cookie = new Cookie(JwtUtil.REFRESH_TOKEN, jwtUtil.createToken(email, JwtUtil.REFRESH_TOKEN));
                    cookie.setHttpOnly(true);
                    cookie.setSecure(true);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    setAuthentication(email);
                } else {
                    jwtExceptionHandler(response, "Refresh 토큰이 유효하지 않습니다.");
                    return;
                }
            } else if (refresh_token == null) {
                jwtExceptionHandler(response, "Access 토큰이 만료되었습니다. Refresh 토큰을 함께 보내주세요.");
                return;
            } else {
                jwtExceptionHandler(response, "Refresh 토큰이 만료되었습니다.");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = jwtUtil.createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 예외 처리 핸들러
    public void jwtExceptionHandler(HttpServletResponse response, String msg) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(StatusCode.BAD_REQUEST, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
