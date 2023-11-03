package com.fubon.ecplatformapi.helper;

import com.fubon.ecplatformapi.model.dto.req.FubonLoginReqDTO;
import com.fubon.ecplatformapi.model.dto.req.FubonVerificationReqDTO;
import com.fubon.ecplatformapi.model.dto.req.LoginReqDTO;


public class JsonHelper {
    public String convertVerificationConfigToJson(FubonVerificationReqDTO config) {
        return "{" + "\"Header\":{" +
                "\"account\":\"" + config.getHeader().getAccount() + "\"," +
                "\"user_ip\":\"" + config.getHeader().getUser_ip() + "\"," +
                "\"SysPwd\":\"" + config.getHeader().getSysPwd() + "\"," +
                "\"FromSys\":\"" + config.getHeader().getFromSys() + "\"," +
                "\"FunctionCode\":\"" + config.getHeader().getFunctionCode() + "\"" +
                "}," + "\"FBECCOMSTA1032RQ\":{" +
                "\"system\":\"" + config.getFBECCOMSTA1032RQ().getSystem() + "\"," +
                "\"insureType\":\"" + config.getFBECCOMSTA1032RQ().getInsureType() + "\"," +
                "\"verificationTypes\":\"" + config.getFBECCOMSTA1032RQ().getVerificationTypes() + "\"" +
                "}" + "}";
    }

    public String convertLoginConfigToJson(FubonLoginReqDTO config, LoginReqDTO loginReq) {
        return "{" + "\"Header\":{" +
                "\"account\":\"" + config.getHeader().getAccount() + "\"," +
                "\"user_ip\":\"" + config.getHeader().getUser_ip() + "\"," +
                "\"SysPwd\":\"" + config.getHeader().getSysPwd() + "\"," +
                "\"FromSys\":\"" + config.getHeader().getFromSys() + "\"," +
                "\"FunctionCode\":\"" + config.getHeader().getFunctionCode() + "\"" +
                "}," + "\"FBECAPPCERT1001RQ\":{" +
                "\"ipAddress\":\"" + config.getFBECAPPCERT1001RQ().getIpAddress() + "\"," +
                "\"device\":\"" + config.getFBECAPPCERT1001RQ().getDevice() + "\"," +
                "\"codeName\":\"" + config.getFBECAPPCERT1001RQ().getCodeName() + "\"," +
                "\"deviceId\":\"" + config.getFBECAPPCERT1001RQ().getDeviceId() + "\"," +
                "\"osVersion\":\"" + config.getFBECAPPCERT1001RQ().getOsVersion() + "\"," +
                "\"appVersion\":\"" + config.getFBECAPPCERT1001RQ().getAppVersion() + "\"," +
                "\"agentId\":\"" + config.getFBECAPPCERT1001RQ().getAgentId() + "\"," +
                "\"loginId\":\"" + config.getFBECAPPCERT1001RQ().getLoginId() + "\"," +
                "\"salesId\":\"" + config.getFBECAPPCERT1001RQ().getSalesId() + "\"," +
                "\"agentName\":\"" + config.getFBECAPPCERT1001RQ().getAgentName() + "\"," +
                "\"unionNum\":\"" + config.getFBECAPPCERT1001RQ().getUnionNum() + "\"," +
                "\"returnPfx\":\"" + config.getFBECAPPCERT1001RQ().getReturnPfx() + "\"," +
                "\"identity\":\"" + config.getFBECAPPCERT1001RQ().getIdentify() + "\"," +
                "\"empNo\":\"" + config.getFBECAPPCERT1001RQ().getEmpNo() + "\"," +
                "\"password\":\"" + config.getFBECAPPCERT1001RQ().getPassword() + "\"," +
                "\"token\":\"" + loginReq.getToken() + "\"," +
                "\"verificationCode\":\"" + loginReq.getVerificationCode() + "\"," +
                "\"client\":\"" + config.getFBECAPPCERT1001RQ().getClient() + "\"," +
                "\"sid\":\"" + config.getFBECAPPCERT1001RQ().getSid() + "\"" +
                "}" + "}";
    }
}
