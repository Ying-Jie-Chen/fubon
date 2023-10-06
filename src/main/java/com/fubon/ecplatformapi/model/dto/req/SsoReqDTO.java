package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SsoReqDTO {
    private String token;
    private String srcSystem;
    private String domain;
}
