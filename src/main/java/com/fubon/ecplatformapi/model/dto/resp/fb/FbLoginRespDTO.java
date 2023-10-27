package com.fubon.ecplatformapi.model.dto.resp.fb;

import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
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
        private GetUserInfoVo getUserInfoVo;
    }

}

