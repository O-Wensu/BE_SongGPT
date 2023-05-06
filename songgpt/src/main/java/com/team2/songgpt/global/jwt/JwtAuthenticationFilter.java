package com.team2.songgpt.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team2.songgpt.entity.Member;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
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
            }
            else if (refresh_token != null) {
                boolean isRefreshToken = jwtUtil.refreshTokenValidation(refresh_token);
                if (!isRefreshToken) {
                    String email = jwtUtil.getUserInfoFromToken(refresh_token);
                    String newAccessToken = jwtUtil.createToken(email, JwtUtil.ACCESS_TOKEN);
                    jwtUtil.setHeaderAccessToken(response, newAccessToken);
                    setAuthentication(jwtUtil.getUserInfoFromToken(newAccessToken));
                } else {
                    response.setContentType("application/json; charset=utf8");
                    ResponseDto responseDto = ResponseDto.setBadRequest("토큰을 찾을 수 없습니다.");
                    String json = new ObjectMapper().writeValueAsString(responseDto);
                    response.getWriter().write(json);
                    return;
                }
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
}
