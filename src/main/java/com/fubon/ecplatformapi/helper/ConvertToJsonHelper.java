package com.fubon.ecplatformapi.helper;

import com.fubon.ecplatformapi.config.EcwsConfig;
import com.fubon.ecplatformapi.controller.auth.SessionController;
import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.model.dto.FubonEcWsLoginReqDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonLoginReqDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonSsoTokenDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonVerificationReqDTO;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ConvertToJsonHelper extends SessionController {
    @Autowired
    private EcwsConfig ecwsConfig;

    public String convertVerificationConfigToJson(FubonVerificationReqDTO config) {
        FubonVerificationReqDTO.Header header = config.getHeader();
        FubonVerificationReqDTO.FBECCOMSTA1032 functionCode = config.getFBECCOMSTA1032RQ();
        return "{" + "\"Header\":{" + "\"account\":\"" + header.getAccount() + "\"," + "\"user_ip\":\"" + header.getUser_ip() + "\"," + "\"SysPwd\":\"" + header.getSysPwd() + "\"," + "\"FromSys\":\"" + header.getFromSys() + "\"," + "\"FunctionCode\":\"" + header.getFunctionCode() + "\"" + "}," + "\"FBECCOMSTA1032RQ\":{" + "\"system\":\"" + functionCode.getSystem() + "\"," + "\"insureType\":\"" + functionCode.getInsureType() + "\"," + "\"verificationTypes\":\"" + functionCode.getVerificationTypes() + "\"" + "}" + "}";
    }

    public String convertLoginConfigToJson(FubonLoginReqDTO config, LoginReqDTO loginReq) {
        FubonLoginReqDTO.Header header = config.getHeader();
        FubonLoginReqDTO.FunctionCode functionCode = config.getFBECAPPCERT1001RQ();

        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("{")
                .append("\"Header\":{")
                .append("\"account\":\"").append(header.getAccount()).append("\",")
                .append("\"user_ip\":\"").append(header.getUser_ip()).append("\",")
                .append("\"SysPwd\":\"").append(header.getSysPwd()).append("\",")
                .append("\"FromSys\":\"").append(header.getFromSys()).append("\",")
                .append("\"FunctionCode\":\"").append(header.getFunctionCode()).append("\"")
                .append("},")
                .append("\"FBECAPPCERT1001RQ\":{")
                .append("\"ipAddress\":\"").append(functionCode.getIpAddress()).append("\",")
                .append("\"device\":\"").append(functionCode.getDevice()).append("\",")
                .append("\"codeName\":\"").append(functionCode.getCodeName()).append("\",")
                .append("\"deviceId\":\"").append(functionCode.getDeviceId()).append("\",")
                .append("\"osVersion\":\"").append(functionCode.getOsVersion()).append("\",")
                .append("\"appVersion\":\"").append(functionCode.getAppVersion()).append("\",")
                .append("\"agentId\":\"").append(functionCode.getAgentId()).append("\",")
                .append("\"loginId\":\"").append(functionCode.getLoginId()).append("\",")
                .append("\"salesId\":\"").append(functionCode.getSalesId()).append("\",")
                .append("\"agentName\":\"").append(functionCode.getAgentName()).append("\",")
                .append("\"unionNum\":\"").append(functionCode.getUnionNum()).append("\",")
                .append("\"returnPfx\":\"").append(functionCode.getReturnPfx()).append("\",")
                .append("\"identity\":\"").append(loginReq.getIdentify()).append("\",")
                .append("\"empNo\":\"").append(loginReq.getAccount()).append("\",")
                .append("\"password\":\"").append(loginReq.getPassword()).append("\",")
                .append("\"token\":\"").append(loginReq.getToken()).append("\",")
                .append("\"verificationCode\":\"").append(loginReq.getVerificationCode()).append("\",")
                .append("\"client\":\"").append(functionCode.getClient()).append("\",")
                .append("\"sid\":\"").append(functionCode.getSid()).append("\"")
                .append("}")
                .append("}");

        return jsonBuilder.toString();
    }

    public String convertSsoTokenToJson(FubonSsoTokenDTO config){
        FubonSsoTokenDTO.Header header = config.getHeader();
        FubonSsoTokenDTO.FunctionCode functionCode = config.getFbeccomsta1040RQ();
        String unionNum = SessionHelper.getValueByAttribute(sessionID(), SessionAttribute.UNION_NUM).toString();
        String account = SessionHelper.getValueByAttribute(sessionID(), SessionAttribute.FBID).toString();
        String parameter = generateCsv(sessionID());

        return "{" + "\"Header\":{" + "\"FromSys\":\"" + header.getFromSys() + "\"," + "\"SysPwd\":\"" + header.getSysPwd() + "\"," + "\"FunctionCode\":\"" + header.getFunctionCode() + "\"" + "}," + "\"FBECCOMSTA1040RQ\":{" + "\"unionNum\":\"" + unionNum + "\"," + "\"account\":\"" + account + "\"," + "\"source\":\"" + functionCode.getSource() + "\"," + "\"desType\":\"" + functionCode.getDesType() + "\"," + "\"desFunction\":\"" + functionCode.getDesFunction() + "\"," + "\"desModule\":\"" + functionCode.getDesModule() + "\"," + "\"type\":\"" + functionCode.getType() + "\"," + "\"parameter\":\"" + parameter  + "\"" + "}" + "}";
    }

    public String generateCsv(String sessionId) {
        List<SessionAttribute> attributes = new ArrayList<>();
        attributes.add(SessionAttribute.EMP_NO);
        attributes.add(SessionAttribute.EMAIL);
        attributes.add(SessionAttribute.FBID);
        attributes.add(SessionAttribute.EMP_NAME);
        attributes.add(SessionAttribute.ADMIN_NUM);
        attributes.add(SessionAttribute.EMP_NO);

        Map<SessionAttribute, String> sessionData = new HashMap<>();

        for (SessionAttribute attribute : attributes) {
            Object value = SessionHelper.getValueByAttribute(sessionId, attribute);
            sessionData.put(attribute, value.toString());
        }

        List<String> csvData = new ArrayList<>();
        for (SessionAttribute attribute : attributes) {
            String value = sessionData.get(attribute);
            csvData.add(attribute.name());
            csvData.add(value != null ? value : "");
        }

        System.out.println("CSV data: " + String.join(",", csvData));

        return String.join(",", csvData);
    }

    public FubonEcWsLoginReqDTO convertFubonEcWsLoginReq(LoginReqDTO loginReq) {
        FubonEcWsLoginReqDTO ecwsLoginReq = new FubonEcWsLoginReqDTO();

        // Header
        ecwsLoginReq.setHeader(
                FubonEcWsLoginReqDTO.Header
                        .builder()
                        .account(loginReq.getAccount())
                        .user_ip("192.168.50.138")
                        .sysPwd(ecwsConfig.getHeader().getSysPwd())
                        .fromSys(ecwsConfig.getHeader().getFromSys())
                        .functionCode(FubonEcWsLoginReqDTO.FUNCTION_CODE)
                        .build()
        );
        // FBECAPPCERT1001
        ecwsLoginReq.setFbecappcert1001Rq(
                FubonEcWsLoginReqDTO.FBECAPPCERT1001RQ
                        .builder()
                        .ipAddress("192.168.50.138")
                        .device("iOS")
                        .codeName("ASUS P01T_1")
                        .deviceId("589241cf66bb05f7")
                        .osVersion("6.0.1")
                        .appVersion("1.01.81")
                        .agentId("")
                        .loginId("")
                        .salesId("")
                        .agentName("")
                        .unionNum("")
                        .adminId("")
                        .returnPfx("1")
                        .identity(loginReq.getIdentify())
                        .empNo(loginReq.getAccount())
                        .password(loginReq.getPassword())
                        .token(loginReq.getToken())
                        .verificationCode(loginReq.getVerificationCode())
                        .client("")
                        .sid("")
                        .build()
        );

        return ecwsLoginReq;
    }
}
