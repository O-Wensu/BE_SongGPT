package com.team2.songgpt.dto.member;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    @Email
    @NotBlank
    private String email;

    @Size(min = 4, max = 6, message = "{nickname.size}")
    @NotBlank
    private String nickname;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*?&()_])[A-Za-z\\d$@$!%*?&()_]{8,15}$", message = "{password.pattern}")
    @NotBlank
    private String password;

}
