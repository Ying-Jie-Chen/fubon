package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class FbecRequest {
    private String returnPfx;
    private String identify;
    private String empNo;
    private String password;
    private String verificationCode;
    private String token;

}
