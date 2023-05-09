package com.team2.songgpt.dto.papago;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PapagoMessage {
    @JsonProperty("result")
    private PapagoResult result;

}
