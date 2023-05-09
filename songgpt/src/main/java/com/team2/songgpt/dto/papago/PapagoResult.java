package com.team2.songgpt.dto.papago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PapagoResult {

    @JsonProperty("translatedText")
    private String translatedtext;
    @JsonProperty("tarLangType")
    private String tarlangtype;
    @JsonProperty("srcLangType")
    private String srclangtype;

    @Builder
    public PapagoResult(String translatedtext, String tarlangtype, String srclangtype) {
        this.translatedtext = translatedtext;
        this.tarlangtype = tarlangtype;
        this.srclangtype = srclangtype;
    }
}
