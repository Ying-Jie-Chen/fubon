package com.fubon.ecplatformapi;

import lombok.Data;
import lombok.Value;

@Data
public class UserInfo {
    private String agent_name;
    private String agent_id;
    private String admin_num;
    private String identify;
    private String unionNum;
    private String id;
    private boolean signed;
    private boolean tested2;

    private XrefInfo xrefInfo;

    @Data
    public static class XrefInfo {
        private String xref;
        private String ascCrzSale;
        private String admin;
    }
}
