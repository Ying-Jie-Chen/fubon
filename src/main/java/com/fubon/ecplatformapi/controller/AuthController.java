package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.model.dto.vo.VerificationImageVO;
import com.fubon.ecplatformapi.model.dto.req.SsoReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.service.CallFubonService;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import com.fubon.ecplatformapi.service.SessionService;
import com.fubon.ecplatformapi.service.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CallFubonService callFubonService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private SsoService ssoService;

    @PostMapping("/loginSSO")
    public ApiRespDTO<UserInfo> SSOLogin(@RequestBody SsoReqDTO ssoReq) {
        try {
            sessionService.getSessionInfo(); // 印出session中的值
            //UserInfo responseData = ssoService.perfornSsoLogin(ssoReq);
            ssoService.perfornSsoLogin(ssoReq);
            return ApiRespDTO.<UserInfo>builder()
                    //.data(responseData)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<UserInfo>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    @PostMapping("/logout")
    public ApiRespDTO<String> logout(){
        try {
            //sessionService.getSessionInfo(); // 印出session中的值
            sessionService.removeSession();

            return ApiRespDTO.<String>builder()
                    .build();
        } catch (Exception e){
            log.error(e.getMessage());
            return  ApiRespDTO.<String>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    @PostMapping("/login")
    public ApiRespDTO<UserInfo> login(@RequestBody LoginReq loginRequest){

        try {
            FbLoginRespDTO fbLoginRespDTO = callFubonService.FBECAPPCERT1001(loginRequest).block();

            assert fbLoginRespDTO != null;
            sessionService.saveSessionInfo(fbLoginRespDTO);
            //sessionService.getSessionInfo(); // 印出session中的值
            UserInfo responseData = fbLoginRespDTO.getAny().getUserInfo();
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
    public ApiRespDTO<VerificationImageVO> getVerificationImage() {
        VerificationImageVO VerificationImageVO = new VerificationImageVO();

        try {
            VerificationResp verificationResp = callFubonService.FBECCOMSTA1032().block();

            assert verificationResp != null;
            String imageBase64 = verificationResp.getAny().getVerificationImageBase64();
            String token = verificationResp.getAny().getToken();

            VerificationImageVO.setVerificationImage(imageBase64);
            VerificationImageVO.setToken(token);

            return ApiRespDTO.<VerificationImageVO>builder()
                    .data(VerificationImageVO)
                    .build();

        } catch (Exception e) {
            log.error(e.getMessage());
            return  ApiRespDTO.<VerificationImageVO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

}
