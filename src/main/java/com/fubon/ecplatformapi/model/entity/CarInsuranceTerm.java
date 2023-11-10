package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
@Data
@Entity
@Table(name = "ec_ws_car_ins_term")
public class CarInsuranceTerm {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long prjCode;

    @Column(name = "term_ins_code")
    private String termInsCode;
    @Column(name = "term_ins_name")
    private String termInsName;

    @Column(name = "term_ins_note1")
    private String termInsNote1;

    @Column(name = "term_ins_content")
    private String termInsContent;

    @Column(name = "term_ins_layout1")
    private String termInsLayout1;

    @Column(name = "term_ins_default1")
    private String termInsDefault1;

    @Column(name = "term_ins_content1")
    private String termInsContent1;

    @Column(name = "term_ins_note2")
    private String termInsNote2;

    @Column(name = "term_ins_layout2")
    private String termInsLayout2;

    @Column(name = "term_ins_default2")
    private String termInsDefault2;

    @Column(name = "term_ins_content2")
    private String termInsContent2;

    @Column(name = "term_ins_note3")
    private String termInsNote3;

    @Column(name = "term_ins_layout3")
    private String termInsLayout3;

    @Column(name = "term_ins_default3")
    private String termInsDefault3;

    @Column(name = "c_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date cTime;

    @Column(name = "m_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date mTime;

    @Column(name = "term_ins_lsaction")
    private String termInsLsAction;

    @Column(name = "term_ins_cartypelimit")
    private String termInsCarTypeLimit;

    @Column(name = "term_ins_api_param1")
    private String termInsApiParam1;

    @Column(name = "term_ins_api_param2")
    private String termInsApiParam2;

    @Column(name = "term_ins_api_param3")
    private String termInsApiParam3;

    @Column(name = "term_ins_unit1")
    private String termInsUnit1;

    @Column(name = "term_ins_unit2")
    private String termInsUnit2;

    @Column(name = "term_ins_unit3")
    private String termInsUnit3;

    @Column(name = "term_memo")
    private String termMemo;

    @Column(name = "term_ins_stop_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date termInsStopTime;

    @Column(name = "term_ins_flag_others")
    private String termInsFlagOthers;

    @Column(name = "term_ins_len_rpoint")
    private Integer termInsLenRPoint;

    @Column(name = "term_ins_wrhfl_grp")
    private String termInsWrhflGrp;

}
