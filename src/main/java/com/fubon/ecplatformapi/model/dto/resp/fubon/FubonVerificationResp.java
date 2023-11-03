package com.fubon.ecplatformapi.model.dto.resp.fubon;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FubonVerificationResp {
    private Header Header;
    private any any;

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
    public static class any {
        private String token;
        private String verificationImageBase64;
    }
}
