package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "licfl")
public class LicenseInfo {

    @Id
    @Column(name = "lic_idno")
    private String licenseId;

    @Column(name = "codeflag")
    private String codeFlag;

}

