package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.QueryReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.DetailResultDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.CreateDetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;
import com.fubon.ecplatformapi.model.entity.QueryReq;
import com.fubon.ecplatformapi.service.CallFubonService;
import com.fubon.ecplatformapi.service.PolicyService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
    @Validated
    public ApiRespDTO<List<QueryResultVO>> queryList(@RequestBody QueryReqDTO req) {
        try {
            //QueryReq queryReq = policyService.createQueryReq(convertToEntity(req));
            //QueryResultVO resultVO = new QueryResultVO();
            //resultVO.setQueryReq(queryReq);

            log.info("Fubon API /QueryList 的回應結果#Start");
            FbQueryResp fbQueryResp = buildResponse.buildListResponse();

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

    private QueryReq convertToEntity(QueryReqDTO dto) {
        return modelMapper.map(dto, QueryReq.class);
    }

}
