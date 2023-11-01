package com.fubon.ecplatformapi.NoUse.captcha;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fubon.ecplatformapi.service.impl.SessionServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CaptchaService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SessionServiceImpl sessionService;


    /** 驗證用戶輸入的驗證碼是否匹配
     *
     */
    public boolean verifyCaptcha(String userInput){
        log.info("驗證用戶輸入的驗證碼#Start");

        String storedCode = sessionService.getSession();
        log.info("取得: " + storedCode);
        if (storedCode == null ) {
            throw new RuntimeException("session 中沒有驗證碼或驗證碼過期");
        }
        if (userInput == null) {
            throw new IllegalArgumentException("沒有輸入驗證碼");
        }

        log.info("比較使用者輸入與預期的驗證碼（忽略大小寫): " + userInput.equalsIgnoreCase(storedCode));
        return userInput.equalsIgnoreCase(storedCode);

    }

    public String generateResponseJson(String system, String insureType, String verificationTypes, String token, String base64String) {

        try {

//            VerificationReq req = VerificationReq.builder()
//                    .Header(VerificationReq.Header.builder()
//                            .FromSys("B2A")
//                            .SysPwd("*****PW8SGg=")
//                            .FunctionCode("FBECCOMSTA1032")
//                            .build())
//                    .FBECCOMSTA1032RQ(VerificationReq.FBECCOMSTA1032.builder()
//                            .system(system)
//                            .insureType(insureType)
//                            .verificationTypes(verificationTypes)
//                            .build())
//                    .build();


//            VerificationResp resp = VerificationResp.builder()
//                    .Header(VerificationResp.Header.builder()
//                            .MsgId("033ef14f-345e-42a9-9114-fbfdd562909f")
//                            .FromSys("ECWS")
//                            .ToSys(req.getHeader().getFromSys())
//                            .SysPwd(req.getHeader().getSysPwd())
//                            .FunctionCode(req.getHeader().getFunctionCode())
//                            .StatusCode("0000")
//                            .StatusDesc("成功")
//                            .build())
//                    .any(VerificationResp.any.builder()
//                            .token(token)
//                            .verificationImageBase64(base64String)
//                            .build())
//                    .build();

//            return objectMapper.writeValueAsString(resp);
            return null;

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
