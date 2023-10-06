package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FbLoginReq {

    private Header Header;
    private FunctionCode FBECAPPCERT1001RQ;


    @Builder
    public static class Header {
        private String FromSys;
        private String SysPwd;
        private String FunctionCode;
    }

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
