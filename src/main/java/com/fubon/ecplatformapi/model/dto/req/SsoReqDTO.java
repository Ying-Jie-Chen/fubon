package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SsoReqDTO {
    private String token;
    private String srcSystem;
    private String domain;
}
