package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.CreateDetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.service.AuthServiceImpl;
import com.fubon.ecplatformapi.service.PolicyServiceImpl;
import com.fubon.ecplatformapi.token.Token;
import com.fubon.ecplatformapi.token.TokenService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/policy")
public class PolicyController {

    @Autowired
    PolicyServiceImpl policyService;
    @Autowired
    AuthServiceImpl authServiceImpl;
    @Autowired
    TokenService tokenService;

    @GetMapping("/qu    eryPolicyList")
    public ApiRespDTO<List<PolicyListResultVO>> queryList(@Valid @RequestBody PolicyListReqDTO req) {

        try {

            log.info("Fubon API /QueryList 的回應結果#Start");
            List<PolicyListResultVO> queryResult = policyService.queryPolicyResults(req);
            //String message = tokenService.validateToken(token, AESKey);
			//log.info("message: " + message);

            return ApiRespDTO.<List<PolicyListResultVO>>builder()
                    .data(queryResult)
                    .build();

        } catch (Exception e) {
            return  ApiRespDTO.<List<PolicyListResultVO>>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }

    @GetMapping("/queryPolicyDetail")
    public ApiRespDTO<CreateDetailResultVO> queryDetail(@Valid @RequestBody PolicyDetailReqDTO request)
    {
        try {
            DetailResultVo detailResult = policyService.getPolicyDetail(request);
            CreateDetailResultVO resultVO = new CreateDetailResultVO();
            resultVO.setDetailResult(detailResult);

            return ApiRespDTO.<CreateDetailResultVO>builder()
                    .data(resultVO)
                    .build();

        } catch (Exception e) {
            return ApiRespDTO.<CreateDetailResultVO>builder()
                    .code(StatusCodeEnum.Err10001.name())
                    .message(StatusCodeEnum.Err10001.getMessage())
                    .build();
        }
    }


}
