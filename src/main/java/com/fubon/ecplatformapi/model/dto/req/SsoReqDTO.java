package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class SsoReqDTO {

    @NotNull
    @JsonProperty("token")
    private String token;

}
