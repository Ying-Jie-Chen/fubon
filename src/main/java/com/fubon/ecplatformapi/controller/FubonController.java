package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.model.dto.resp.FubonLoginResp;
import com.fubon.ecplatformapi.model.dto.resp.VerificationResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

        return buildResponse.buildLoginResponse();
    }



}
