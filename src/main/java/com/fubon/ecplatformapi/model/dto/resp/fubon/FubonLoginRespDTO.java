package com.fubon.ecplatformapi.model.dto.resp.fubon;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class FubonLoginRespDTO {
    @JsonProperty("Header")
    private Header Header;
    @JsonProperty("any")
    private Any any;

    @Data
    public static class Header {
        @JsonProperty("MsgId")
        private String MsgId;
        @JsonProperty("FromSys")
        private String FromSys;
        @JsonProperty("ToSys")
        private String ToSys;
        @JsonProperty("SysPwd")
        private String SysPwd;
        @JsonProperty("FunctionCode")
        private String FunctionCode;
        @JsonProperty("StatusCode")
        private String StatusCode;
        @JsonProperty("StatusDesc")
        private String StatusDesc;
    }

    @Data
    public static class Any {
        @JsonProperty("staffValid")
        private boolean staffValid ;
        @JsonProperty("staffValidMsg")
        private String staffValidMsg;
        @JsonProperty("pfx")
        private String pfx;
        @JsonProperty("cerId")
        private String certId;
        @JsonProperty("serverPublicKey")
        private String serverPublicKey;
        @JsonProperty("userInfo")
        private UserInfo userInfo;
        private String userInfoParam;
        private String authPolicy;
        private String certNumber;
    }

    @Data
    public static class UserInfo {

        private String agent_name;
        private String agent_id;
        private String admin_num;
        private String identity;
        private String union_num;
        private String email;
        private String id;
        private String sales_id;
        private boolean signed;
        private boolean tested;
        private boolean tested2;
        private String applyflag;
        private String agentworkunna2;
        private String agentworkun3;
        private String agentworkunna3;
        private String agentworkun2;
        private String adminworkunna2;
        private String adminworkun3;
        private String adminworkunna3;
        private String adminname;
        private String adminworkun2;

        @JsonProperty("xrefInfo")
        private List<XrefInfo> xrefInfo;

        @Data
        @Builder
        public static class XrefInfo {
            private String xref;
            private String channel;
            private String regno;
            private String empcname;
            private String adminSeq;
            private String ascCrzSale;
            private String admin;
            private String isctype;
            private String licempcname;
        }
    }

}


