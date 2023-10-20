package com.fubon.ecplatformapi.token;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Token {

    public String tokenHeader;

    private String token;

    public boolean revoked;

    public boolean expired;

}
