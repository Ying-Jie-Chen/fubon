package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
public class QueryReq {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;

    @NonNull
    private String insType;

    @NonNull
    private String plate;

    @NonNull
    private Integer queryType;

    @NonNull
    private String insurerName;

    @NonNull
    private String insurerId;

    @NonNull
    private Date effectDateStart;

    @NonNull
    private Date effectDateEnd;

    @NonNull
    private String managerId;

    @NonNull
    private String policyNum;

}
