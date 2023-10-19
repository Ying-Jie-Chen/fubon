package com.fubon.ecplatformapi.token;

import lombok.Data;

@Data
public class Token {

    public TokenHeader tokenHeader = TokenHeader.BEARER;

    public boolean revoked;

    public boolean expired;
}
