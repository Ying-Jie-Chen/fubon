package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.dto.resp.LoginResponse;
import jakarta.servlet.http.HttpServletRequest;

/**
 * Verification Image Base64
 */
public class ValidateCode {
    LoginResponse loginResponse;
    public String getRandomCodeBase64(HttpServletRequest request){
        String base64String = "";
        loginResponse.setData(base64String);
        return base64String;
    }
}
