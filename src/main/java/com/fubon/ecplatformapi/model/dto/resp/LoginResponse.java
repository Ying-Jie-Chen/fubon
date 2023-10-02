package com.fubon.ecplatformapi.model.dto.resp;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class LoginResponse {
    private String code;
    private String message;
    private Object data;
}
