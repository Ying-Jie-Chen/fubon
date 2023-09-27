package com.fubon.ecplatformapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    private UserInfoRepository userInfoRepository;
    // 驗證圖形驗證碼，假設使用 validateVerificationCode 方法
//        if (StringUtils.pathEquals(request.getVerificationCode(), request.getToken())) {
//        }

    public LoginResponse login(LoginRequest request) {
        log.info("展業平台 API 請求參數: ");
        LoginRequest loginRequest = new LoginRequest(
                request.getIdentify(),
                request.getAccount(),
                request.getPassword(),
                request.getVerificationCode(),
                request.getToken()
        );
        log.info(loginRequest.toString());

        try {
            /*
            判斷身份驗證結果欄位(staffValid)如果為true，則回傳狀態碼為 [0000] 成功。
            如果為 false，則回傳狀態碼為[1001] - 登入驗證失敗 [富邦API回應結果欄位(staffValidMsg)]
             */
            var userInfo = userInfoRepository.findByEmail(request.getAccount());

//            if (response != null && response.isStaffValid()) {
            if(userInfo.isPresent()){
                return LoginResponse.builder()
                        .code(ReturnCodeEnum.SUCCESS.getCode())
                        .message(ReturnCodeEnum.SUCCESS.getMsg())
                        .data(userInfo)
                        .build();
            } else {
                return LoginResponse.builder()
                        .code(ReturnCodeEnum.FAILED.getCode())
//                        .message(ReturnCodeEnum.FAILED.getMsg() + userInfo.getStaffValidMsg())
                        .data(userInfo)
                        .build();
            }
        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
    }

}


