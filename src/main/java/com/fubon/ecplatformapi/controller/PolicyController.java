package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.service.PolicyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Autowired
    PolicyService policyService;


    @GetMapping("/queryPolicyList")
    public ApiRespDTO<List<QueryResultVO>> queryPolicy(
            @RequestParam("insType") String insType,
            @RequestParam("plate") String plate,
            @RequestParam("queryType") Integer queryType,
            @RequestParam("insurerName") String insurerName,
            @RequestParam("insurerId") String insurerId,
            @RequestParam("effectDateStart") Date effectDateStart,
            @RequestParam("effectDateEnd") Date effectDateEnd,
            @RequestParam("managerId") String managerId,
            @RequestParam("policyNum") String policyNum) {

        try {

            //FbQueryResp fbQueryResp = callFubonService.queryResponse(queryReqDTO).block();
            List<QueryResultVO> queryResult = policyService.getPolicyList();

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

}
