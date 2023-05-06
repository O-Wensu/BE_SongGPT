package com.team2.songgpt.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {
    public String email;
    public String password;
}
