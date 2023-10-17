package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.ResultMapper;
import com.fubon.ecplatformapi.ValidationException;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.DetailResultDTO;
import com.fubon.ecplatformapi.model.dto.vo.CreateDetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.service.CallFubonService;
import com.fubon.ecplatformapi.service.PolicyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private ResultMapper resultMapper;
    @Autowired
    PolicyService policyService;
    @Autowired
    CallFubonService callFubonService;

    @GetMapping("/queryPolicyList")
    public ApiRespDTO<List<PolicyListResultVO>> queryList(@Valid @RequestBody  PolicyListReqDTO req) {

        try {

            policyService.isRequestValid(req);

            log.info("Fubon API /QueryList 的回應結果#Start");
            List<PolicyListResultVO> queryResult = policyService.callQueryResp()
                    .flatMapMany(fbQueryRespDTO -> Flux.fromIterable(fbQueryRespDTO.getPolicyResults()))
                    .map(resultMapper::mapToResultVO)
                    .collectList()
                    .block();

            return ApiRespDTO.<List<PolicyListResultVO>>builder()
                    .data(queryResult)
                    .build();

        } catch (ValidationException e) {
            return  ApiRespDTO.<List<PolicyListResultVO>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(e.getMessage())
                    .build();

        } catch (Exception e) {
            log.error(e.toString());
            return  ApiRespDTO.<List<PolicyListResultVO>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    @GetMapping("/queryPolicyDetail")
    public ApiRespDTO<CreateDetailResultVO> queryDetail(@RequestParam String insType,
                                                        @RequestParam String policyNum)
    {
        try {
            DetailResultDTO detailResult = policyService.getPolicyDetail(insType, policyNum);
            CreateDetailResultVO resultVO = new CreateDetailResultVO();
            resultVO.setDetailResult(detailResult);

            return ApiRespDTO.<CreateDetailResultVO>builder()
                    .data(resultVO)
                    .build();

        } catch (Exception e) {
            log.error(e.toString());
            return ApiRespDTO.<CreateDetailResultVO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }


}
