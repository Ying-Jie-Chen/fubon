package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.ParameterValid;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.QueryReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.service.PolicyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Autowired
    private BuildResponse buildResponse;
    @Autowired
    PolicyService policyService;

    @GetMapping("/queryPolicyList")
    @Validated
    public ApiRespDTO<List<QueryResultVO>> queryPolicy(
            @RequestBody @Valid QueryReqDTO queryReqDTO
//            @ParameterValid @RequestParam("insType") String insType,
//            @ParameterValid @RequestParam(required = false) String plate,
//            @ParameterValid @RequestParam(required = true) Integer queryType,
//            @RequestParam("insurerName") String insurerName,
//            @RequestParam("insurerId") String insurerId,
//            @RequestParam("effectDateStart") Date effectDateStart,
//            @RequestParam("effectDateEnd") Date effectDateEnd,
//            @RequestParam("managerId") String managerId,
//            @RequestParam("policyNum") String policyNum
            ) {

        try {
            log.info(queryReqDTO.getInsType());
            FbQueryResp fbQueryResp = buildResponse.buildQueryResponse();
            //FbQueryResp fbQueryResp = callFubonService.queryResponse(queryReqDTO).block();
            List<QueryResultVO> queryResult = policyService.getPolicyList(fbQueryResp);

            return ApiRespDTO.<List<QueryResultVO>>builder()
                    .data(queryResult)
                    .build();

        } catch (Exception e) {
            log.error(e.toString());
            return ApiRespDTO.<List<QueryResultVO>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    private boolean isValidInsType(String insType) {
        return  "MOT".equals(insType) || "CQCCX".equals(insType) || "CHCRX".equals(insType) ||
                "CTX".equals(insType) || "CGX".equals(insType) || "FIR".equals(insType) ||
                "ENG".equals(insType) || "MGO".equals(insType) || "CAS".equals(insType);
    }

}
