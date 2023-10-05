package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Slf4j
@RestController
public class FubonController {

    @Autowired
    private BuildResponse buildResponse;

    @GetMapping ("/GetVerificationImage")
    public VerificationResp GetVerificationImage() {
        return buildResponse.buildVerificationImageResponse();
    }

    @PostMapping("/Login")
    public FubonLoginResp Login() {

        return buildResponse.buildLoginResponse(createUserInfo());
    }

    @PostMapping("/Logout")
    public FubonLoginResp Logout() {

        return buildResponse.buildLoginResponse(createUserInfo());
    }


    public UserInfo createUserInfo() {
        UserInfo.XrefInfo xrefInfo = UserInfo.XrefInfo.builder()
                .xref("經辦代號")
                .channel("通路號")
                .ascCrzSale("業務員ID")
                .admin("管理人員編")
                .build();

        return UserInfo.builder()
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
