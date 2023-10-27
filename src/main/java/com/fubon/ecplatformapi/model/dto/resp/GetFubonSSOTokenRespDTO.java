package com.fubon.ecplatformapi.model.dto.resp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class GetFubonSSOTokenRespDTO {

    private Header Header;
    private Any Any;

    @Data
    @Builder
    public static class Header {
        private String MsgId;
        private String FromSys;
        private String SysPwd;
        private String FunctionCode;
        private String StatusCode;
        private String StatusDesc;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Any {
        private String sid;
    }
}
