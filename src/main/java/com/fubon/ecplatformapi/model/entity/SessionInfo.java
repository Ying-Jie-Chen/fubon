package com.fubon.ecplatformapi.model.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class SessionInfo {
    private String identify;
    private String empNo;
    private String empName;
    private String fbid;
    private List<XrefInfo> xrefInfos;

    @Data
    @Builder
    public static class XrefInfo {
        private String xref;
        private String channel;
        private String ascCrzSale;
        private String admin;
    }
}
