package com.fubon.ecplatformapi.model.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "revoked")
    public Boolean revoked;

    @Column(name = "expired")
    public Boolean expired;


}
