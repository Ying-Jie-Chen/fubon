package com.fubon.ecplatformapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fubon.ecplatformapi.config.EcwsConfig;
import com.fubon.ecplatformapi.config.SessionManager;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.exception.CustomException;
import com.fubon.ecplatformapi.helper.ConvertToJsonHelper;
import com.fubon.ecplatformapi.model.dto.FubonEcWsLoginReqDTO;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.model.dto.vo.VerificationVo;
import com.fubon.ecplatformapi.model.entity.AccountEntity;
import com.fubon.ecplatformapi.model.entity.LoginLogEntity;
import com.fubon.ecplatformapi.repository.AccountRepository;
import com.fubon.ecplatformapi.repository.LoginLogRepository;
import com.fubon.ecplatformapi.service.AuthService;
import com.fubon.ecplatformapi.service.TokenService;
import com.fubon.ecplatformapi.service.XrefInfoService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    TokenService tokenService;
    @Autowired
    EcwsConfig ecwsConfig;
    @Autowired
    ConvertToJsonHelper jsonHelper;
    @Autowired
    XrefInfoService xrefInfoService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    LoginLogRepository loginLogRepository;

    private final WebClient webClient;

    @Autowired
    public AuthServiceImpl(WebClient.Builder webClientBuilder, EcwsConfig ecwsConfig) {
        this.ecwsConfig = ecwsConfig;
        this.webClient = webClientBuilder.baseUrl(ecwsConfig.getDomain()).build();
    }

    /**
     * 取得 Fubon API /GetVerificationImage 的回應結果
     *
     */
    @Override
    public VerificationVo getVerificationImage() {
        String jsonRequest = jsonHelper.convertVerificationConfigToJson(ecwsConfig.verificationConfig());

        VerificationResp verificationResp = callFubonService(jsonRequest, VerificationResp.class).block();

        String imageBase64 = verificationResp != null ?
                verificationResp.getAny().getVerificationImageBase64() : null;
        String token = verificationResp != null ?
                verificationResp.getAny().getToken() : null;

        return VerificationVo.builder()
                .verificationImage(imageBase64)
                .token(token)
                .build();

    }

    /**
     * 取得 Fubon API /Login 的回應結果
     *
     */
    @Override
    public LoginRespVo getUserInfo(LoginReqDTO loginReq, HttpSession session) throws Exception{
        String loginAccount = loginReq.getAccount();

        FubonEcWsLoginReqDTO fubonEcWsLoginReqDTO = jsonHelper.convertFubonEcWsLoginReq(loginReq);
        LoginRespDTO fbLoginRespDTO = callFubonService(objectMapper.writeValueAsString(fubonEcWsLoginReqDTO), LoginRespDTO.class).block();

        LoginLogEntity loginLog = createLoginRecord(loginAccount);
        handleFubonLoginResp(loginLog, fbLoginRespDTO);
        //loginLogRepository.save(loginLog);

        SessionManager.saveSession(session, fbLoginRespDTO);
        String authToken = tokenService.generateToken(session.getId(), loginReq.getAccount(), System.currentTimeMillis());
        tokenService.saveAuthToken(session, authToken);

        LoginRespVo.ResponseData data = xrefInfoService.getXrefInfoList(fbLoginRespDTO);

        //updateOrCreateAccount(loginAccount, data);

        return LoginRespVo.builder()
                .token(authToken)
                .data(data)
                .build();

    }

    /**
     *  登入成功後新增使用者帳號或更新最後登入時間
     */
    private void updateOrCreateAccount(String loginAccount, LoginRespVo.ResponseData data) {
        log.debug("查詢帳號是否存在");
        Optional<AccountEntity> optionalAccount = accountRepository.findByAccount(loginAccount);

        if (optionalAccount.isPresent()) {
            log.debug("帳號存在，更新最近登入時間");
            AccountEntity existingAccount = optionalAccount.get();
            existingAccount.setLastLoginTime(new Date());
            //accountRepository.save(existingAccount);
        } else {
            log.debug("帳號不存在，新增帳號資料");
            LoginRespVo.UserInfo userInfo = data.getUserInfo();
            AccountEntity newAccount = new AccountEntity();
            newAccount.setAccount(loginAccount);
            newAccount.setName(userInfo.getAgentName());
            newAccount.setEmpNo(userInfo.getAgentId());
            newAccount.setIdentify(userInfo.getIdentity());
            newAccount.setFirstLoginTime(new Date());
            newAccount.setLastLoginTime(new Date());
            //accountRepository.save(newAccount);
        }
    }

    private void handleFubonLoginResp(LoginLogEntity loginLog, LoginRespDTO fbLoginRespDTO) throws Exception{
        String errorMsg = null;
        try {
            if (fbLoginRespDTO != null) {
                if (!fbLoginRespDTO.getHeader().getStatusCode().equals("0000")) {
                    errorMsg = fbLoginRespDTO.getHeader().getStatusDesc();
                    loginLog.setLoginResult(0);
                    loginLog.setLoginRemark(errorMsg);
                }
                if (!fbLoginRespDTO.getAny().getStaffValid()) {
                    errorMsg = fbLoginRespDTO.getAny().getStaffValidMsg();
                    loginLog.setLoginResult(0);
                    loginLog.setLoginRemark(errorMsg);
                }
                if (fbLoginRespDTO.getAny().getUserInfo().getTested() || fbLoginRespDTO.getAny().getUserInfo().getTested2()) {
                    fbLoginRespDTO.getAny().getUserInfo().setTested(true);
                    fbLoginRespDTO.getAny().getUserInfo().setTested2(true);
                }
                log.debug("Error message: " + errorMsg);
            }
            loginLog.setLoginResult(1);
            log.debug(objectMapper.writeValueAsString(fbLoginRespDTO));

        } catch (NullPointerException e) {
            //loginLogRepository.save(loginLog);
            throw new CustomException(errorMsg, StatusCodeEnum.ERR00999.getCode());
        }
    }

    /**
     * 展業平台登入記錄: 登入成功或失敗皆新增一筆
     *
     */
    private LoginLogEntity createLoginRecord(String loginAccount) {
        LoginLogEntity loginLog = new LoginLogEntity();
        loginLog.setAccount(loginAccount);
        loginLog.setLoginTime(new Date());
        return loginLog;
    }


    private <T> Mono<T> callFubonService(String jsonRequest, Class<T> responseType) {

        return webClient
                .post()
                .uri("")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(jsonRequest)
                .retrieve()
                .bodyToMono(responseType);

    }

    @Override
    public void saveVerificationImage(String outputPath, String base64String){
        byte[] imageBytes = Base64.getDecoder().decode(base64String);
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            fos.write(imageBytes);
            log.info("驗證碼圖片成功存到: " + outputPath);
        } catch (IOException e) {
            log.error("保存驗證碼圖片錯誤: " + e.getMessage());
        }
    }

}
