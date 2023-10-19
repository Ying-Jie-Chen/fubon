package com.fubon.ecplatformapi.model.dto.req;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 展業平台 API
 * 登入參數
 */
@Data
@JsonIgnoreProperties(value = {"password"}, allowSetters = true)
public class LoginReq {

    @NotNull(message = "identify 不可為空值")
    @JsonProperty("identify")
    private String identify;

    @NotNull(message = "account 不可為空值")
    @JsonProperty("account")
    private String account;

    @NotNull(message = "password 不可為空值")
    @JsonProperty("password")
    private String password;

    @JsonProperty("verificationCode")
    private String verificationCode;

    @JsonProperty("token")
    private String token;

}
