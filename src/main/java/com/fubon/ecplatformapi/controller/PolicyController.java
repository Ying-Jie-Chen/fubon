package com.fubon.ecplatformapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.ValidationException;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.DetailResultDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.CreateDetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.model.entity.PolicyListReq;
import com.fubon.ecplatformapi.service.CallFubonService;
import com.fubon.ecplatformapi.service.PolicyService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BuildResponse buildResponse;
    @Autowired
    PolicyService policyService;
    @Autowired
    CallFubonService callFubonService;

    @GetMapping("/queryPolicyList")
    public ApiRespDTO<List<PolicyListResultVO>> queryList(@Valid @RequestBody  PolicyListReqDTO req) {
        try {

            //PolicyListReq policyListReq = policyService.createQueryReq(convertToEntity(req));

//            policyService.isRequestValid(policyListReq);
//            PolicyListResultVO resultVO = new PolicyListResultVO();
//            resultVO.setInsType(policyListReq.getInsType());
//            String insType = policyListReq.getInsType();
//            log.info("insType = " + insType);
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("Fubon API /QueryList 的回應結果#Start");
//            Mono<List<FbQueryRespDTO>> queryResp = policyService.callQueryResp();
//            List<PolicyListResultVO> queryResult = queryResp
//                    .map(fbQueryRespList -> fbQueryRespList.stream()
//                            .map(this::mapToPolicyResultVo)
//                            .collect(Collectors.toList()))
//                    .block();

            List<PolicyListResultVO> queryResult = policyService.callQueryResp()
                    .flatMapMany(Flux::fromIterable)
                    .map(this::mapToPolicyResultVo)
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
            //FbQueryResp fbQueryResp = buildResponse.buildDetailResponse();
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

    private PolicyListReq convertToEntity(PolicyListReqDTO dto) {
        return modelMapper.map(dto, PolicyListReq.class);
    }


    public PolicyListResultVO mapToPolicyResultVo(FbQueryRespDTO fbQueryResp) {
        return modelMapper.map(fbQueryResp, PolicyListResultVO.class);
    }
    public List<PolicyListResultVO> mapToResults(List<FbQueryRespDTO.PolicyResult> fbQueryResults) {
        List<PolicyListResultVO> results = new ArrayList<>();
        for (FbQueryRespDTO.PolicyResult fbQueryResult : fbQueryResults) {
            PolicyListResultVO policyResultVo = new PolicyListResultVO();

            policyResultVo.setInsType(fbQueryResult.getClsGrp());
            policyResultVo.setPolicyNum(fbQueryResult.getPolFormatid());

            results.add(policyResultVo);
        }
        return results;
    }


}
