package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FubonLoginReq {

    private Header Header;
    private FunctionCode FBECAPPCERT1001RQ;

    @Data
    @Builder
    public static class Header {
        private String FromSys;
        private String SysPwd;
        private String FunctionCode;
    }
    @Data
    @Builder
    public static class FunctionCode {
        private String returnPfx;
        private String identify;
        private String empNo;
        private String password;
        private String verificationCode;
        private String token;
    }
}
