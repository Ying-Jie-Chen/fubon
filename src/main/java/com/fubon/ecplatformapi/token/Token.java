package com.fubon.ecplatformapi.token;


import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class Token {

    public String tokenHeader;

    private String token;

    @Builder.Default
    public Boolean revoked = false;

    @Builder.Default
    public Boolean expired = false;

    private Duration expirationTime;

    private String sessionId;
    private String empNo;
    private long timestamp;
    private String signature;

}
