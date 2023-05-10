package com.team2.songgpt.controller;

import com.team2.songgpt.dto.member.*;
import com.team2.songgpt.global.dto.ResponseDto;
import com.team2.songgpt.global.security.UserDetailsImpl;
import com.team2.songgpt.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Tag(name = "Member Controller", description = "회원 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @Operation(summary = "get member", description = "회원 정보 조회",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK" ),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @GetMapping
    public ResponseDto<MemberResponseDto> getMember(HttpServletRequest request) {
        return memberService.getMember(request);
    }

    @Operation(summary = "sign up", description = "회원 가입")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = SignupRequestDto.class)))
    @PostMapping("/register")
    public ResponseDto<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        return memberService.signup(signupRequestDto);
    }

    @Operation(summary = "login", description = "로그인")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation = ResponseDto.class)))
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody( content = @Content(schema = @Schema(implementation = LoginRequestDto.class)))
    @PostMapping("/login")
    public ResponseDto<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return memberService.login(loginRequestDto, response);
    }

    @Operation(summary = "logout", description = "로그아웃",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @PostMapping("/logout")
    public ResponseDto<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return memberService.logout(request, response);
    }

    @Operation(hidden = true)
    @GetMapping("/newAccess")
    public ResponseDto<?> callNewAccessToken(@CookieValue(value = "Refresh_Token", required = false) String refreshToken, HttpServletRequest request, HttpServletResponse response) {
        return memberService.callNewAccessToken(refreshToken, request, response);
    }

    @Operation(summary = "Update Profile", description = "회원 이미지 변경",
            security = { @SecurityRequirement(name = "Access_Token"),  @SecurityRequirement(name = "Refresh_Token")})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })
    @PostMapping(value = "/profile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseDto<String> updateProfile(@RequestParam(value = "image") MultipartFile image, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return memberService.updateProfile(image, userDetails.getMember());
    }
}
