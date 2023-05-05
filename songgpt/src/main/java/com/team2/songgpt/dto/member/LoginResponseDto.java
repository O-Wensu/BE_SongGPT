package com.team2.songgpt.dto.member;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team2.songgpt.entity.Member;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class LoginResponseDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("nickname")
    private String nickname;

    public LoginResponseDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
