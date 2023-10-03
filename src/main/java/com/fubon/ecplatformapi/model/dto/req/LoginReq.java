package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

/**
 * 展業平台 API
 * 登入參數
 */
@Data
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class LoginReq {

    private String identify;
    private String account;
    private String password;
    private String verificationCode;
    @JsonProperty("token")
    private String token;

}
