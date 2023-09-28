package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Value;
@Entity
@Data
public class UserInfo {
    private String agent_name;
    private String agent_id;
    private String admin_num;
    private String identify;
    private String unionNum;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
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
