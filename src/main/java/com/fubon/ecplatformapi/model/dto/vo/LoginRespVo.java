package com.fubon.ecplatformapi.model.dto.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginRespVo {

    @JsonProperty("token")
    private String token;

    private String statusDesc;

    private ResponseData data;

    @Data
    @Builder
    public static class ResponseData {
        @JsonProperty("userInfo")
        private UserInfo userInfo;
        @JsonProperty("xrefInfo")
        private List<LoginRespDTO.XrefInfo> xrefInfo;
    }

    @Data
    @Builder
    public static class UserInfo {
        private String agentName;
        private String agentId;
        private String adminNum;
        private String identity;
        private String email;
        private String unionNum;
        private String id;
        private boolean signed;
        private boolean tested2;
    }
}



