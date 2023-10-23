package com.fubon.ecplatformapi.token;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

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

//    public String tokenHeader;
//    private Duration expirationTime;
//    private String sessionId;
//    private String empNo;
//    private long timestamp;
//    private String signature;


}
