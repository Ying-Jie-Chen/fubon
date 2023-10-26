package com.fubon.ecplatformapi.model.dto.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRespVo {

    @JsonProperty("token")
    private String token;
    private GetUserInfoVo getUserInfoVo;

}