package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


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
