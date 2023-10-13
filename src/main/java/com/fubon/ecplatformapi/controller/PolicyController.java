package com.fubon.ecplatformapi.controller;

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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ApiRespDTO<PolicyListResultVO> queryList(@Valid @RequestBody  PolicyListReqDTO req) {
        try {

            //PolicyListReq policyListReq = policyService.createQueryReq(convertToEntity(req));

//            policyService.isRequestValid(policyListReq);
//            PolicyListResultVO resultVO = new PolicyListResultVO();
//            resultVO.setInsType(policyListReq.getInsType());
//            String insType = policyListReq.getInsType();
//            log.info("insType = " + insType);

            log.info("Fubon API /QueryList 的回應結果#Start");
            //FbQueryRespDTO fbQueryRespDTO = buildResponse.buildListResponse();
            PolicyListResultVO queryResult = policyService.callQueryResp();
            //List<PolicyListResultVO> queryResult = policyService.getPolicyList(fbQueryRespDTO);

            return ApiRespDTO.<PolicyListResultVO>builder()
                    .data(queryResult)
                    .build();

        } catch (ValidationException e) {
            return  ApiRespDTO.<PolicyListResultVO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(e.getMessage())
                    .build();
        } catch (Exception e) {
            log.error(e.toString());
            return  ApiRespDTO.<PolicyListResultVO>builder()
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


}
