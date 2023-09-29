package com.fubon.ecplatformapi.model.dto.req;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class VerificationReq {
        private Header Header;
        private FBECCOMSTA1032RQ FBECCOMSTA1032RQ;

        @Data
        @Builder
        public static class Header {
                private String FromSys;
                private String SysPwd;
                private String FunctionCode;
        }
        @Data
        @Builder
        public static class FBECCOMSTA1032RQ {
                private String system;
                private String insureType;
                private String verificationTypes;
        }
}
