package com.fubon.ecplatformapi.model.dto.resp;

import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FbLoginRespDTO {

    private Header Header;
    private Any Any;

    @Data
    @Builder
    public static class Header {
        private String MsgId;
        private String FromSys;
        private String ToSys;
        private String SysPwd;
        private String FunctionCode;
        private String StatusCode;
        private String StatusDesc;
    }

    @Data
    @Builder
    public static class Any {
        private boolean staffValid ;
        private String staffValidMsg;
        private UserInfo userInfo;
    }

}

