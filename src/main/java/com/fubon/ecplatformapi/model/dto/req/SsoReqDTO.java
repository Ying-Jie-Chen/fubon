package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SsoReqDTO {

    @NotNull
    @JsonProperty("token")
    private String token;

    //private String srcSystem;
    //private String domain;
}
