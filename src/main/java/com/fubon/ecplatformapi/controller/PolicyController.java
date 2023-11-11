package com.fubon.ecplatformapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fubon.ecplatformapi.controller.auth.SessionController;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.CreateDetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.service.impl.PolicyServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController extends SessionController {

    @Autowired
    PolicyServiceImpl policyService;


    @GetMapping("/queryPolicyList")
    public ApiRespDTO<List<PolicyListResultVO>> queryList(@Valid @RequestBody PolicyListReqDTO req) {

        try {
            log.info("Fubon API /QueryList 的回應結果#Start");
            List<PolicyListResultVO> queryResult = policyService.queryPolicyResults(req);

            return ApiRespDTO.<List<PolicyListResultVO>>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    .authToken(getAuthToken())
                    .data(queryResult)
                    .build();

        } catch(Exception e){
                return ApiRespDTO.<List<PolicyListResultVO>>builder()
                        .code(StatusCodeEnum.ERR00999.name())
                        .message(StatusCodeEnum.ERR00999.getMessage())
                        .build();
        }
    }
    @Autowired
    ObjectMapper objectMapper;

    @GetMapping("/queryPolicyDetail")
    public ApiRespDTO<CreateDetailResultVO> queryDetail(@Valid @RequestBody PolicyDetailReqDTO request)
    {
        try {
            DetailResultVo detailResult = policyService.getPolicyDetail(request);

//            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
//            String jsonRequest;
//            try {
//                jsonRequest = objectMapper.writeValueAsString(detailResult);
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(jsonRequest);

            CreateDetailResultVO resultVO = new CreateDetailResultVO();
            resultVO.setDetailResult(detailResult);

            return ApiRespDTO.<CreateDetailResultVO>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    .authToken(getAuthToken())
                    .data(resultVO)
                    .build();

        } catch (Exception e) {
            return ApiRespDTO.<CreateDetailResultVO>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }


}
