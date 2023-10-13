package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@Table(name = "list_req")
@NoArgsConstructor
public class PolicyListReq {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ins_type")
    private String insType;

    @Column(name = "plate")
    private String plate;

    @Column(name = "query_type")
    private Integer queryType;

    @Column(name = "insurer_name")
    private String insurerName;

    @Column(name = "insurer_id")
    private String insurerId;

    @Column(name = "effect_date_start")
    private Date effectDateStart;

    @Column(name = "effect_date_end")
    private Date effectDateEnd;

    @Column(name = "manager_id")
    private String managerId;

    @Column(name = "policy_num")
    private String policyNum;

}
