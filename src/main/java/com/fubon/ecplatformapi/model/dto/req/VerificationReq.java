package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class VerificationReq {
        private Header Header;
        private FBECCOMSTA1032 FBECCOMSTA1032RQ;

        @Getter
        @Builder
        public static class Header {
                private String FromSys;
                private String SysPwd;
                private String FunctionCode;
                private String account;
                private String user_ip;
        }
        @Getter
        @Builder
        public static class FBECCOMSTA1032 {
                private String system;
                private String insureType;
                private String verificationTypes;
        }

}
