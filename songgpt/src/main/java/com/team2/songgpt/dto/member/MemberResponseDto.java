package com.team2.songgpt.dto.member;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team2.songgpt.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@Schema(description = "회원 정보 변경 요청 DTO")
public class MemberResponseDto {
    @JsonProperty("email")
    private String email;
    @JsonProperty("nickname")
    private String nickname;

    private String image;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.image = member.getImageUrl();
    }
}
