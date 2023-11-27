package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Data
@Table(name = "NFNV03")
public class NFNV03Entity {

    @Column(name = "INSLIN")
    private String inslin;

    @Column(name = "INSDESC")
    private String insdesc;

    @Column(name = "POLYNO_Q")
    private String polynoQ;

    @Id
    @Column(name = "POLYNO")
    private String polyno;

    @Column(name = "TRADE_NO")
    private String tradeNo;

    @Column(name = "PAYAMT")
    private BigDecimal payamt;

    @Column(name = "ISRID")
    private String isrid;

    @Column(name = "ISRNM")
    private String isrnm;

    @Column(name = "INSID")
    private String insid;

    @Column(name = "INSNM")
    private String insnm;

    @Column(name = "EFFDA")
    private Date effda;

    @Column(name = "EXPDA")
    private Date expda;

    @Column(name = "PCLLDA")
    private Date pcllda;

    @Column(name = "FUNDKIND")
    private String fundkind;

    @Column(name = "ISC_ADMIN")
    private String iscAdmin;

    @Column(name = "ISC_XREF")
    private String iscXref;

    @Column(name = "PRSNID")
    private String prsnid;
}