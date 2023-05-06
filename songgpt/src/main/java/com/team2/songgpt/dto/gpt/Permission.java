package com.team2.songgpt.dto.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
public class Permission {
    @JsonProperty("is_blocking")
    private boolean isBlocking;

    @JsonProperty("organization")
    private String organization;

    @JsonProperty("allow_fine_tuning")
    private boolean allowFineTuning;

    @JsonProperty("allow_view")
    private boolean allowView;

    @JsonProperty("allow_search_indices")
    private boolean allowSearchIndices;

    @JsonProperty("allow_logprobs")
    private boolean allowLogprobs;

    @JsonProperty("allow_sampling")
    private boolean allowSampling;

    @JsonProperty("allow_create_engine")
    private boolean allowCreateEngine;

    @JsonProperty("created")
    private int created;

    @JsonProperty("object")
    private String object;

    @JsonProperty("id")
    private String id;

    @Builder
    public Permission(boolean isBlocking, String organization, boolean allowFineTuning, boolean allowView, boolean allowSearchIndices, boolean allowLogprobs, boolean allowSampling, boolean allowCreateEngine, int created, String object, String id) {
        this.isBlocking = isBlocking;
        this.organization = organization;
        this.allowFineTuning = allowFineTuning;
        this.allowView = allowView;
        this.allowSearchIndices = allowSearchIndices;
        this.allowLogprobs = allowLogprobs;
        this.allowSampling = allowSampling;
        this.allowCreateEngine = allowCreateEngine;
        this.created = created;
        this.object = object;
        this.id = id;
    }
}
