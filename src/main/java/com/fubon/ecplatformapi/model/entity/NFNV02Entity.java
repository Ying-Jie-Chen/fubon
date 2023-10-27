package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Data
@Table(name = "nfnv02")
public class NFNV02Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inslin", length = 3)
    private String inslin;

    @Column(name = "insdesc", length = 10)
    private String insdesc;

    @Column(name = "polyno_q", length = 16)
    private String polynoQ;

    @Column(name = "polyno", length = 16)
    private String polyno;

    @Column(name = "seqno")
    private Integer seqno;

    @Column(name = "trade_no", length = 16)
    private String tradeNo;

    @Column(name = "premiu", precision = 13, scale = 2)
    private BigDecimal premiu;

    @Column(name = "isrid", length = 20)
    private String isrid;

    @Column(name = "isrnm", length = 160)
    private String isrnm;

    @Column(name = "insid", length = 20)
    private String insid;

    @Column(name = "insnm", length = 160)
    private String insnm;

    @Column(name = "platno", length = 11)
    private String platno;

    @Column(name = "issda")
    private Date issda;

    @Column(name = "effda")
    private Date effda;

    @Column(name = "expda")
    private Date expda;

    @Column(name = "isc_admin", length = 7)
    private String iscAdmin;

    @Column(name = "isc_xref", length = 9)
    private String iscXref;

    @Column(name = "prsnid", length = 20)
    private String prsnid;

    @Column(name = "addr_code", length = 7)
    private String addrCode;

    @Column(name = "addr", length = 160)
    private String addr;

}
