package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class LoginReq {
    @Id
    private String identify;
    private String account;
    private String password;
    private String verificationCode;
    @JsonProperty("token")
    private String token;

}
