package com.fubon.ecplatformapi.NoUse.captcha;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class CaptchaController {
    @Autowired
    CaptchaUtil captchaUtil;

    /** 取得登入頁面中的圖形驗證碼
     *
     * @param request
     * @param response
     * @return
     */

    //@GetMapping("/getVerificationImage")
    public ResponseEntity<ApiRespDTO<Map<String, Object>>>  getVerificationImage(HttpServletRequest request, HttpServletResponse response) {
        try {
                response.setContentType("image/png");
                String base64String = captchaUtil.generateCaptchaBase64();
                String token = "Get from Fubon API";

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("verificationImage", base64String);
                responseData.put("token", token);

                ApiRespDTO<Map<String, Object>> responseDto = successResponse(responseData);
                return new ResponseEntity<>(responseDto, HttpStatus.OK);

        } catch (Exception e) {

            ApiRespDTO<Map<String, Object>> responseDto = errorResponse();
            return new ResponseEntity<>(responseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ApiRespDTO<Map<String, Object>> errorResponse() {
        return ApiRespDTO.<Map<String, Object>>builder()
                .code(StatusCodeEnum.ERR00999.name())
                .message(StatusCodeEnum.ERR00999.getMessage())
                .data(null)
                .build();
    }

    private ApiRespDTO<Map<String, Object>> successResponse(Map<String, Object> responseData) {
        return ApiRespDTO.<Map<String, Object>>builder()
                .data(responseData)
                .build();
    }


}

