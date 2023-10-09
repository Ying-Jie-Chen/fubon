package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;
import com.fubon.ecplatformapi.model.dto.req.QueryReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.service.CallFubonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
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
            List<QueryResultVO.QueryResult> resultData = new ArrayList<>();

            for (FbQueryResp.PolicyResult policyResult : policyResults) {
                QueryResultVO.QueryResult queryResult = new QueryResultVO.QueryResult();

                queryResult.setInsType(policyResult.getClsGrp());
                queryResult.setPolicyNum(policyResult.getPolFormatid());
                queryResult.setPremiums(policyResult.getUnPaidPrm());
                queryResult.setInsuredName(policyResult.getRmaClinameI());
                queryResult.setPlate(policyResult.getMohPlatno());

                resultData.add(queryResult);
            }

            QueryResultVO queryResultVO = new QueryResultVO();
            queryResultVO.setData(resultData);

            return ApiRespDTO.<QueryResultVO>builder()
                    .data(queryResultVO)
                    .build();

        } catch (Exception e) {
            log.error(e.toString());
            return ApiRespDTO.<QueryResultVO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

}
