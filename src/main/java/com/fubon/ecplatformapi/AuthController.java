package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.captcha.CaptchaUtil;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.captcha.VerificationService;
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
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    VerificationService verificationService;
    @Autowired
    CaptchaUtil captchaUtil;

    // 取得登入頁面中的圖形驗證碼，還沒寫如何生成token!!
    @GetMapping("/getVerificationImage")
    public ResponseEntity<ApiRespDTO<Map<String, Object>>>  getVerificationImage(HttpServletRequest request, HttpServletResponse response) {
        try {
                response.setContentType("image/png");
                String base64String = captchaUtil.generateCaptchaBase64();
                String token = "token123456";

                Map<String, Object> responseData = new HashMap<>();
                responseData.put("verificationImage", base64String);
                responseData.put("token", token);

                ApiRespDTO<Map<String, Object>> successResponse = ApiRespDTO.<Map<String, Object>>builder()
                        .data(responseData)
                        .build();

                return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {

                ApiRespDTO<Map<String, Object>> errorResponse = ApiRespDTO.<Map<String, Object>>builder()
                        .code(StatusCodeEnum.Err10001.name())
                        .message(StatusCodeEnum.Err10001.getMessage())
                        .build();

                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // 在登入驗證時驗證使用者輸入的驗證碼
    @PostMapping("/login")
    public ResponseEntity<ApiRespDTO<FubonLoginResp>> login(@RequestBody LoginReq loginRequest) {
        return null;
    }


    private ResponseEntity<ApiRespDTO<FubonLoginResp>> createErrorResponse(HttpStatus status, StatusCodeEnum code, String errorMsg) {
        ApiRespDTO<FubonLoginResp> response = ApiRespDTO.<FubonLoginResp>builder()
                .code(code.name())
                .message(code.getMessage())
                //.data(errorMsg)
                .build();
        return ResponseEntity.status(status).body(response);
    }

}

