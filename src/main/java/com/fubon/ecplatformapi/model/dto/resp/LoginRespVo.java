package com.fubon.ecplatformapi.model.dto.resp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRespVo {

    @JsonProperty("token")
    private String token;
    private UserInfo userInfo;

}