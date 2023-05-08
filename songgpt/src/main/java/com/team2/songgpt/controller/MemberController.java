package com.team2.songgpt.controller;

import com.team2.songgpt.dto.member.LoginRequestDto;
import com.team2.songgpt.dto.member.LoginResponseDto;
import com.team2.songgpt.dto.member.MemberResponseDto;
import com.team2.songgpt.dto.member.SignupRequestDto;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    public ResponseDto<MemberResponseDto> getMember(HttpServletRequest request) {
        return memberService.getMember(request);
    }

    @PostMapping("/register")
    public ResponseDto<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

    @PostMapping("/login")
    public ResponseDto<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @PostMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return memberService.logout(request, response);
    }

    @GetMapping("/newAccess")
    public ResponseDto<?> callNewAccessToken(@CookieValue("Refresh_Token") String refreshToken, HttpServletResponse response) {
        return memberService.callNewAccessToken(refreshToken, response);
    }
}
