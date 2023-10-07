package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.FbLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;
import com.fubon.ecplatformapi.model.dto.req.QueryReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.service.CallFubonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Autowired
    CallFubonService callFubonService;

    @GetMapping("/queryPolicyList")
    public ApiRespDTO<QueryResultVO> queryPolicy(
            @RequestParam("insType") String insType,
            @RequestParam("queryType") Integer queryType,
            @RequestParam("insurerName") String insurerName,
            @RequestParam("insurerId") String insurerId,
            @RequestParam("effectDateStart") Date effectDateStart,
            @RequestParam("effectDateEnd") Date effectDateEnd,
            @RequestParam("managerId") String managerId,
            @RequestParam("policyNum") String policyNum) {

        try {
            QueryReqDTO queryReqDTO = new QueryReqDTO();
            queryReqDTO.setPolicyNum(insType);
            queryReqDTO.setQueryType(queryType);
            queryReqDTO.setInsurerName(insurerName);
            queryReqDTO.setInsurerId(insurerId);
            queryReqDTO.setEffectDateStart(effectDateStart);
            queryReqDTO.setEffectDateEnd(effectDateEnd);
            queryReqDTO.setManagerId(managerId);
            queryReqDTO.setPolicyNum(policyNum);

            FbQueryResp fbQueryResp = callFubonService.queryResponse(queryReqDTO).block();

            assert fbQueryResp != null;
            List<FbQueryResp.PolicyResult> policyResults = fbQueryResp.getPolicyResults();
            QueryResultVO resultData = new QueryResultVO();

            for (FbQueryResp.PolicyResult policyResult : policyResults) {

                resultData.setInsType(policyResult.getClsGrp());
                resultData.setPolicyNum(policyResult.getPolFormatid());
                resultData.setPremiums(policyResult.getUnPaidPrm());
                resultData.setInsuredName(policyResult.getRmaClinameI());
                resultData.setPlate(policyResult.getMohPlatno());
            }

            return ApiRespDTO.<QueryResultVO>builder()
                    .data(resultData)
                    .build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return ApiRespDTO.<QueryResultVO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

}
