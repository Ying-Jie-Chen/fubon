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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {
    @Autowired
    CallFubonService callFubonService;

    @GetMapping("/queryPolicyList")
    public ApiRespDTO<QueryResultVO> queryPolicy(@RequestBody QueryReqDTO queryReq){

        try {
            FbQueryResp fbQueryResp = callFubonService.queryResponse(queryReq).block();
            List<FbQueryResp.PolicyResult> policyResults = fbQueryResp.getPolicyResults();
            List<QueryResultVO> resultData = new ArrayList<>();

            for (FbQueryResp.PolicyResult policyResult : policyResults) {
                QueryResultVO queryResultVO = new QueryResultVO();
                queryResultVO.setInsType(policyResult.getClsGrp());
                queryResultVO.setPolicyNum(policyResult.getPolFormatid());
                queryResultVO.setPremiums(policyResult.getUnPaidPrm());
                queryResultVO.setInsuredName(policyResult.getRmaClinameI());
                queryResultVO.setPlate(policyResult.getMohPlatno());

                resultData.add(queryResultVO);
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
