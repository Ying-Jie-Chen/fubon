package com.fubon.ecplatformapi;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;

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
