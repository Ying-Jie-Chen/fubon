package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.model.dto.VerificationImageDTO;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import com.fubon.ecplatformapi.model.entity.SessionInfo;
import com.fubon.ecplatformapi.service.CallFubonService;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CallFubonService callFubonService;
    @Autowired
    private SessionService sessionService;

    @PostMapping("/login")
    public ApiRespDTO<UserInfo> login(@RequestBody LoginReq loginRequest){

        try {
            FubonLoginResp fubonLoginResp = callFubonService.FBECAPPCERT1001(loginRequest).block();

            assert fubonLoginResp != null;
            SessionInfo sessionInfo = sessionService.createSessionInfo(fubonLoginResp);
            sessionService.saveSessionInfo(sessionInfo);

            UserInfo responseData = fubonLoginResp.getAny().getUserInfo();
            return ApiRespDTO.<UserInfo>builder()
                    .data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<UserInfo>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }



    @GetMapping("/getVerificationImage")
    public ApiRespDTO<VerificationImageDTO> getVerificationImage() {
        VerificationImageDTO verificationImageDTO = new VerificationImageDTO();
        String imageBase64 = null;
        String token = null;

        try {
            VerificationResp verificationResp = callFubonService.FBECCOMSTA1032().block();

            imageBase64 = verificationResp.getAny().getVerificationImageBase64();
            token = verificationResp.getAny().getToken();

            verificationImageDTO.setVerificationImage(imageBase64);
            verificationImageDTO.setToken(token);

            return ApiRespDTO.<VerificationImageDTO>builder()
                    .data(verificationImageDTO)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<VerificationImageDTO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

}
