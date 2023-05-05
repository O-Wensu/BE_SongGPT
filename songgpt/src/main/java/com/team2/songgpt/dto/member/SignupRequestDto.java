package com.team2.songgpt.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SignupRequestDto {
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    @NotBlank
    private String nickname;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,15}$")
    @NotBlank
    private String password;

}
