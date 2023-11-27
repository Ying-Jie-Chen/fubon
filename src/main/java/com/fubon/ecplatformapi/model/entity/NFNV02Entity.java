package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Data
@Table(name = "NFNV02")
public class NFNV02Entity {

    @Column(name = "INSLIN", length = 3)
    private String inslin;

    @Column(name = "INSDESC", length = 10)
    private String insdesc;

    @Column(name = "POLYNO_Q", length = 16)
    private String polynoQ;

    @Id
    @Column(name = "POLYNO", length = 16)
    private String polyno;

    @Column(name = "SEQNO", length = 22)
    private Integer seqno;

    @Column(name = "TRADE_NO", length = 16)
    private String tradeNo;

    @Column(name = "PREMIU", precision = 11, scale = 2)
    private BigDecimal premiu;

    @Column(name = "ISRID", length = 20)
    private String isrid;

    @Column(name = "ISRNM", length = 320)
    private String isrnm;

    @Column(name = "INSID", length = 20)
    private String insid;

    @Column(name = "INSNM", length = 320)
    private String insnm;

    @Column(name = "PLATNO", length = 11)
    private String platno;

    @Column(name = "ISSDA", length = 7)
    private Date issda;

    @Column(name = "EFFDA", length = 7)
    private Date effda;

    @Column(name = "EXPDA", length = 7)
    private Date expda;

    @Column(name = "ISC_ADMIN", length = 7)
    private String iscAdmin;

    @Column(name = "ISC_XREF", length = 9)
    private String iscXref;

    @Column(name = "PRSNID", length = 20)
    private String prsnid;

    @Column(name = "ADDR_CDE", length = 3)
    private String addrCode;

    @Column(name = "ADDR", length = 510)
    private String addr;

}
