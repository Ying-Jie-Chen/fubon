package com.fubon.ecplatformapi.helper;

import com.fubon.ecplatformapi.controller.other.SessionController;
import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.model.dto.req.FubonLoginReqDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonSsoTokenDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonVerificationReqDTO;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class JsonHelper extends SessionController {

    public String convertVerificationConfigToJson(FubonVerificationReqDTO config) {
        FubonVerificationReqDTO.Header header = config.getHeader();
        FubonVerificationReqDTO.FBECCOMSTA1032 functionCode = config.getFBECCOMSTA1032RQ();
        return "{" + "\"Header\":{" + "\"account\":\"" + header.getAccount() + "\"," + "\"user_ip\":\"" + header.getUser_ip() + "\"," + "\"SysPwd\":\"" + header.getSysPwd() + "\"," + "\"FromSys\":\"" + header.getFromSys() + "\"," + "\"FunctionCode\":\"" + header.getFunctionCode() + "\"" + "}," + "\"FBECCOMSTA1032RQ\":{" + "\"system\":\"" + functionCode.getSystem() + "\"," + "\"insureType\":\"" + functionCode.getInsureType() + "\"," + "\"verificationTypes\":\"" + functionCode.getVerificationTypes() + "\"" + "}" + "}";
    }

    public String convertLoginConfigToJson(FubonLoginReqDTO config, LoginReqDTO loginReq) {
        FubonLoginReqDTO.Header header = config.getHeader();
        FubonLoginReqDTO.FunctionCode functionCode = config.getFBECAPPCERT1001RQ();

        return "{" + "\"Header\":{" + "\"account\":\"" + header.getAccount() + "\"," + "\"user_ip\":\"" + header.getUser_ip() + "\"," + "\"SysPwd\":\"" + header.getSysPwd() + "\"," + "\"FromSys\":\"" + header.getFromSys() + "\"," + "\"FunctionCode\":\"" + header.getFunctionCode() + "\"" + "}," + "\"FBECAPPCERT1001RQ\":{" + "\"ipAddress\":\"" + functionCode.getIpAddress() + "\"," + "\"device\":\"" + functionCode.getDevice() + "\"," + "\"codeName\":\"" + functionCode.getCodeName() + "\"," + "\"deviceId\":\"" + functionCode.getDeviceId() + "\"," + "\"osVersion\":\"" + functionCode.getOsVersion() + "\"," + "\"appVersion\":\"" + functionCode.getAppVersion() + "\"," + "\"agentId\":\"" + functionCode.getAgentId() + "\"," + "\"loginId\":\"" + functionCode.getLoginId() + "\"," + "\"salesId\":\"" + functionCode.getSalesId() + "\"," + "\"agentName\":\"" + functionCode.getAgentName() + "\"," + "\"unionNum\":\"" + functionCode.getUnionNum() + "\"," + "\"returnPfx\":\"" + functionCode.getReturnPfx() + "\"," + "\"identity\":\"" + functionCode.getIdentify() + "\"," + "\"empNo\":\"" + functionCode.getEmpNo() + "\"," + "\"password\":\"" + functionCode.getPassword() + "\"," + "\"token\":\"" + loginReq.getToken() + "\"," + "\"verificationCode\":\"" + loginReq.getVerificationCode() + "\"," + "\"client\":\"" + functionCode.getClient() + "\"," + "\"sid\":\"" + functionCode.getSid() + "\"" + "}" + "}";
    }

    public String convertSsoTokenToJson(FubonSsoTokenDTO config){
        FubonSsoTokenDTO.Header header = config.getHeader();
        FubonSsoTokenDTO.FunctionCode functionCode = config.getFbeccomsta1040RQ();
        String unionNum = SessionHelper.getValueByAttribute(sessionID(), SessionAttribute.UNION_NUM);
        String account = SessionHelper.getValueByAttribute(sessionID(), SessionAttribute.FBID);
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
}
