package com.fubon.ecplatformapi.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
public class LoginRespDTO {
    @JsonProperty("Header")
    private Header Header;
    @JsonProperty("any")
    private Any any;

    @Data
    public static class Header {
        @JsonProperty("MsgId")
        private String msgId;
        @JsonProperty("FromSys")
        private String fromSys;
        @JsonProperty("ToSys")
        private String toSys;
        @JsonProperty("SysPwd")
        private String sysPwd;
        @JsonProperty("FunctionCode")
        private String functionCode;
        @JsonProperty("StatusCode")
        private String statusCode;
        @JsonProperty("StatusDesc")
        private String statusDesc;
    }

    @Data
    public static class Any {

        private boolean staffValid ;
        private String staffValidMsg;
        private String pfx;
        private String certId;
        private String serverPublicKey;
        @JsonProperty("userInfo")
        private UserInfo userInfo;
        private String userInfoParam;
        private String authPolicy;
        @JsonProperty("xrefInfo")
        private List<XrefInfo> xrefInfo;
        private String certNumber;

        public boolean getStaffValid(){ return  staffValid; }
    }

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class UserInfo {
        private String agent_name;
        private String agent_id;
        private String admin_num;
        private Integer identity;
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
        public boolean getSigned(){
            return signed;
        }
        public boolean getTested(){
            return tested;
        }
        public boolean getTested2(){
            return tested2;
        }
    }

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
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