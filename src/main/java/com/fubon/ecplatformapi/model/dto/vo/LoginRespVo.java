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

    private ResponseData data;

    @Data
    @Builder
    public static class ResponseData {
        @JsonProperty("userInfo")
        private LoginRespDTO.UserInfo userInfo;
        @JsonProperty("xrefInfo")
        private List<LoginRespDTO.XrefInfo> xrefInfo;
    }
}



