package com.fubon.ecplatformapi.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;


@Entity
@Data
@NoArgsConstructor
public class PolicyDetail {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer Id;

    @NonNull
    @Size(max = 5)
    private String insType;

    @NonNull
    @Size(max = 14)
    private String policyNum;
}
