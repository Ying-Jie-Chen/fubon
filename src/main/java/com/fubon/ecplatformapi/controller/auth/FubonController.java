package com.fubon.ecplatformapi.controller.auth;


import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.model.dto.req.*;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.resp.SSOTokenRespDTO;
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
    public SSOTokenRespDTO getSSOToken(){return buildResponse.buildSSOTokenResponse();}

    //@PostMapping ("/queryPolicy")
    public FbQueryRespDTO QueryList(@RequestBody PolicyListReqDTO req){
        return buildResponse.buildListResponse();
    }

    @PostMapping("/policyDetail")
    public FubonPolicyDetailRespDTO getPolicyDetail(GetPolicyDetailReqDTO reqDTO){ return  buildResponse.buildPolicyDetailResponse(); }
    @PostMapping("/getPrnDetail")
    public FubonPrnDetailResp getPrnDetail(GetPrnDetailReqDTO reqDTO){return buildResponse.buildPrnDetailResponse();}
    @PostMapping("/ClmSalesAppWs/api101")
    public FubonClmSalesRespDTO getClmSales(GetClmSalesReqDTO reqDTO){return buildResponse.buildClmSalesResponse(); }
    @PostMapping("/chkEnrData")
    public FubonChkEnrDataRespDTO getEnrData(GetChkEnrDataReqDTO reqDTO){return buildResponse.buildChkEnrData();}
    @PostMapping("/queryPolicy")
    public QueryPolicyListRespDTO getMpPolicyList(GetPolicyListReqDTO reqDTO){return  buildResponse.buildMyPolicyList(); }
    @PostMapping ("/GetVerificationImage")
    public VerificationResp GetVerificationImage(FubonVerificationReqDTO fubonVerificationReqDTO) {
        return buildResponse.buildVerificationImageResponse();
    }

    @PostMapping("/Login")
    public LoginRespDTO Login(@RequestBody LoginReqDTO loginReqDTO) {

        return buildResponse.buildLoginResponse(createUserInfo());
    }

    @PostMapping("/Logout")
    public LoginRespDTO Logout() {

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
