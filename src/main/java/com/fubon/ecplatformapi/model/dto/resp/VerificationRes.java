package com.fubon.ecplatformapi.model.dto.resp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationRes {
    private Header Header;
    private Any any;

    @Data
    @Builder
    public static class Header {
        private String MsgId;
        private String FromSys;
        private String ToSys;
        private String SysPwd;
        private String FunctionCode;
        private String StatusCode;
        private String StatusDesc;
    }

    @Data
    @Builder
    public static class Any {
        private String token;
        private String verificationImageBase64;
    }
}
