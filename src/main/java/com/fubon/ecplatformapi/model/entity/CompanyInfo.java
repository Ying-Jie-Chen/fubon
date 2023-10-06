package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
@Getter
@Entity
@Table(name = "unionc")
public class CompanyInfo {
    @Id
    @Column(name = "union_num")
    private String companyCode;

    @Column(name = "type")
    private String companyType;

}
