package com.fubon.ecplatformapi.controller;


import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.config.VerificationConfig;
import com.fubon.ecplatformapi.model.dto.resp.fb.*;
import com.fubon.ecplatformapi.model.dto.resp.GetFubonSSOTokenRespDTO;
import com.fubon.ecplatformapi.model.dto.req.LoginReq;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.dto.vo.GetUserInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
public class FubonController {

    @Autowired
    private BuildResponse buildResponse;

    @GetMapping("/GetSSOToken")
    public GetFubonSSOTokenRespDTO getSSOToken(){return buildResponse.buildSSOTokenResponse();}

    @PostMapping ("/queryPolicy")
    public FbQueryRespDTO QueryList(@RequestBody PolicyListReqDTO req){
        return buildResponse.buildListResponse();
    }

    @GetMapping("/policyDetail")
    public FubonPolicyDetailRespDTO getPolicyDetail(){ return  buildResponse.buildPolicyDetailResponse(); }

    @GetMapping("/getPrnDetail")
    public FubonPrnDetailResp getPrnDetail(){return buildResponse.buildPrnDetailResponse();}
    @GetMapping("/ClmSalesAppWs/api101")
    public FubonClmSalesRespDTO getClmSales(){return buildResponse.buildClmSalesResponse(); }
    @PostMapping ("/GetVerificationImage")
    public VerificationResp GetVerificationImage(VerificationConfig verificationConfig) {
        return buildResponse.buildVerificationImageResponse();
    }

    @PostMapping("/Login")
    public FbLoginRespDTO Login(@RequestBody LoginReq loginReq) {

        return buildResponse.buildLoginResponse(createUserInfo());
    }

    @PostMapping("/Logout")
    public FbLoginRespDTO Logout() {

        return buildResponse.buildLoginResponse(createUserInfo());
    }


    public GetUserInfoVo createUserInfo() {
        GetUserInfoVo.XrefInfo xrefInfo = GetUserInfoVo.XrefInfo.builder()
                .xref("經辦代號")
                .channel("通路號")
                .ascCrzSale("業務員ID")
                .admin("管理人員編")
                .build();

        return GetUserInfoVo.builder()
                .agent_name("姓名")
                .agent_id("經辦代號")
                .admin_num("管理人員編")
                .identify("身份別")
                .email("電子信箱")
                .unionNum("公司別")
                .id("身份證字號")
                .signed(true)
                .tested2(true)
                .xrefInfo(Collections.singletonList(xrefInfo))
                .build();
    }




}
