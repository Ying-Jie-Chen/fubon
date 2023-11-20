package com.fubon.ecplatformapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
public class FubonEcWsLoginReqDTO {

    public final static String FUNCTION_CODE = "FBECAPPCERT1001";

    @JsonProperty("Header")
    private Header header;

    @JsonProperty("FBECAPPCERT1001RQ")
    private FBECAPPCERT1001RQ fbecappcert1001Rq;

    @Data
    @Builder
    public static class Header {

        @JsonProperty("FromSys")
        private String fromSys;

        @JsonProperty("SysPwd")
        private String sysPwd;

        @JsonProperty("FunctionCode")
        private String functionCode;

        private String account;

        private String user_ip;
    }

    @Data
    @Builder
    public static class FBECAPPCERT1001RQ {

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

        private String identity;

        private String empNo;

        private String password;

        private String verificationCode;

        private String token;

        private String client;

        private String sid;
    }
}