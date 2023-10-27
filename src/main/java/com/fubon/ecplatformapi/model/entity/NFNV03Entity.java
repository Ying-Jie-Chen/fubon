package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Entity
@Data
@Table(name = "nfnv03")
public class NFNV03Entity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "inslin")
    private String inslin;

    @Column(name = "insdesc")
    private String insdesc;

    @Column(name = "polyno_q")
    private String polynoQ;

    @Column(name = "polyno")
    private String polyno;

    @Column(name = "trade_no")
    private String tradeNo;

    @Column(name = "payamt")
    private BigDecimal payamt;

    @Column(name = "isrid")
    private String isrid;

    @Column(name = "isrnm")
    private String isrnm;

    @Column(name = "insid")
    private String insid;

    @Column(name = "insnm")
    private String insnm;

    @Column(name = "effda")
    private Date effda;

    @Column(name = "expda")
    private Date expda;

    @Column(name = "pcllda")
    private Date pcllda;

    @Column(name = "fundkind")
    private String fundkind;

    @Column(name = "isc_admin")
    private String iscAdmin;

    @Column(name = "isc_xref")
    private String iscXref;

    @Column(name = "prsnid")
    private String prsnid;
}
