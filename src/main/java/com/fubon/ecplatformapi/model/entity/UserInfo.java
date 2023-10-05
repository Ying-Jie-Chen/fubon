package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class UserInfo {

    private String agent_name;
    private String agent_id;
    private String admin_num;
    private String identify;
    private String email;
    private String unionNum;
    private String id;
    private boolean signed;
    private boolean tested2;

    private List<XrefInfo> xrefInfo;

    @Data
    @Builder
    public static class XrefInfo {
        private String xref;
        private String channel;
        private String ascCrzSale;
        private String admin;
    }
}
