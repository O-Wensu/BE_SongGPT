package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Schema(description = "모델 상태 대답 DTO")
public class CheckModelResponseDto implements Serializable {

    @JsonProperty("permission")
    private List<Permission> permission;
    @JsonProperty("owned_by")
    private String ownedBy;
    @JsonProperty("object")
    private String object;
    @JsonProperty("id")
    private String id;

    @Builder
    public CheckModelResponseDto(List<Permission> permission, String ownedBy, String object, String id) {
        this.permission = permission;
        this.ownedBy = ownedBy;
        this.object = object;
        this.id = id;
    }
}