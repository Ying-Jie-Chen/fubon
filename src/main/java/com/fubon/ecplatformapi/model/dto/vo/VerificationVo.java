package com.fubon.ecplatformapi.model.dto.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationVo {
    private String verificationImage;
    private String token;
}
