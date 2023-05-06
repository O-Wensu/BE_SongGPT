package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Setter
@NoArgsConstructor
public class CheckModelResponseDto implements Serializable {

    @JsonProperty("permission")
    private List<Permission> permission;
    @JsonProperty("owned_by")
    private String ownedBy;
    @JsonProperty("created")
    private int created;
    @JsonProperty("object")
    private String object;
    @JsonProperty("id")
    private String id;

    @Builder
    public CheckModelResponseDto(List<Permission> permission, String ownedBy, int created, String object, String id) {
        this.permission = permission;
        this.ownedBy = ownedBy;
        this.created = created;
        this.object = object;
        this.id = id;
    }
}