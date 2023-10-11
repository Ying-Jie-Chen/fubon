package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.ParameterValid;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.QueryReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.ListResultVO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.service.CallFubonService;
import com.fubon.ecplatformapi.service.PolicyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
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
    @Autowired
    CallFubonService callFubonService;

    @GetMapping("/queryPolicyList")
    @Validated
    public ApiRespDTO<List<ListResultVO>> queryList(@RequestParam String insType, @RequestParam(required = false) String plate,
            @RequestParam(required = true) Integer queryType, @RequestParam String insurerName, @RequestParam String insurerId,
            @RequestParam Date effectDateStart, @RequestParam Date effectDateEnd, @RequestParam String managerId, @RequestParam String policyNum) {
        try {

            log.info("Fubon API /QueryList 的回應結果#Start");
            FbQueryResp fbQueryResp = buildResponse.buildListResponse();

            List<ListResultVO> queryResult = policyService.getPolicyList(fbQueryResp);

            return ApiRespDTO.<List<ListResultVO>>builder()
                    .data(queryResult)
                    .build();

        } catch (Exception e) {
            log.error(e.toString());
            return ApiRespDTO.<List<ListResultVO>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    @GetMapping("/queryPolicyDetail")
    public ApiRespDTO<List<DetailResultVO>> queryDetail(@RequestParam @Max(5) String insType,
                                                        @RequestParam @Max(14) String policyNum)
    {
        try {
            //FbQueryResp fbQueryResp = buildResponse.buildDetailResponse();
            List<DetailResultVO> policyDetailResult = policyService.getPolicyDetail(insType, policyNum);

            return ApiRespDTO.<List<DetailResultVO>>builder()
                    .data(policyDetailResult)
                    .build();

        } catch (Exception e) {
            log.error(e.toString());
            return ApiRespDTO.<List<DetailResultVO>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

}
