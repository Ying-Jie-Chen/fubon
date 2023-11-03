package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "ecws.login")
public class FubonLoginReqDTO {

    private Header Header;

    private FunctionCode FBECAPPCERT1001RQ;

    @Data
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class Header {

        private String FromSys;

        private String SysPwd;

        private String FunctionCode;

        private String account;

        private String user_ip;
    }

    @Data
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    public static class FunctionCode {

        private String ipAddress;

        private String device;

        private String codeName;

        private String deviceId;

        private String osVersion;

        private String appVersion;

        private String agentId;

        private String loginId;

        private String salesId;

        private String agentName;

        private String unionNum;

        private String adminId;

        private String returnPfx;

        private String identify;

        private String empNo;

        private String password;

        private String verificationCode;

        private String token;

        private String client;

        private String sid;
    }
}
