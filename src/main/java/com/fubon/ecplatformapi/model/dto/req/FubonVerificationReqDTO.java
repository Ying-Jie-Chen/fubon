package com.fubon.ecplatformapi.model.dto.req;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ecws.get-verification-image")
public class FubonVerificationReqDTO {

        private Header header;

        private FBECCOMSTA1032 FBECCOMSTA1032RQ;
        @Data
        public static class Header {

                private String FromSys;

                private String SysPwd;

                private String FunctionCode;

                private String account;

                private String user_ip;
        }
        @Data
        public static class FBECCOMSTA1032 {

                private String system;

                private String insureType;

                private String verificationTypes;
        }

}
