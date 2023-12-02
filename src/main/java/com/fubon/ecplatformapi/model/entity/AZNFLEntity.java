package com.fubon.ecplatformapi.model.entity.fgisdb;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "DBO.AZNFL")
public class AZNFLEntity {

    @Id
    @Column(name = "AZN_CODE")
    private String postalCode;

    @Column(name = "AZN_SNAME")
    private String regionName;

}
