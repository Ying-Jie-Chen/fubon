package com.fubon.ecplatformapi.model.dto.req;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ecws.sso-token")
public class FubonSsoTokenDTO {
    private Header header;
    private FunctionCode fbeccomsta1040RQ;
    @Data
    public static class Header{
        private String fromSys;
        private String sysPwd;
        private String functionCode;
        private String account;
        private String userIp;
    }
    @Data
    public static class FunctionCode{
        private String unionNum;
        private String account;
        private String source;
        private String desType;
        private String desFunction;
        private String desModule;
        private String type;
        private String parameter;
    }
}
