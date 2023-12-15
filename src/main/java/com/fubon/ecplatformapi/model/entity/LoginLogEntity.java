package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "account_login_log")
public class LoginLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private Long logId;

    @Column(name = "account")
    private String account;

    @Column(name = "login_result")
    private Integer loginResult;

    @Column(name = "login_remark")
    private String loginRemark;

    @Column(name = "login_time")
    private Date loginTime;
}
