package com.fubon.ecplatformapi.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @Column(name = "account")
    private String account;

    @Column(name = "name")
    private String name;

    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "[identity]")
    private Integer identify;

    @Column(name = "first_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firstLoginTime;

    @Column(name = "last_login_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;

}
