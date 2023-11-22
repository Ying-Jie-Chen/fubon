package com.fubon.ecplatformapi.controller;

import com.fubon.ecplatformapi.controller.auth.SessionController;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.QueryPolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.CreateDetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.MyPolicyListVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.service.PolicyService;
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
    PolicyService policyService;

    @GetMapping("/queryPolicyList")
    public ApiRespDTO<List<PolicyListResultVO>> queryList(@Valid @RequestBody PolicyListReqDTO req) {

        try {
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


    @GetMapping("/queryPolicyDetail")
    public ApiRespDTO<CreateDetailResultVO> queryDetail(@Valid @RequestBody QueryPolicyDetailReqDTO request) {
        try {

            DetailResultVo detailResult = policyService.getPolicyDetail(request);

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

    @GetMapping("/queryMyPolicyList")
    public  ApiRespDTO<List<MyPolicyListVO>> queryMyPolicyList(){
        try {

            List<MyPolicyListVO> myPolicyList = policyService.getMyPolicyList();

            return ApiRespDTO.<List<MyPolicyListVO>>builder()
                    .code(StatusCodeEnum.SUCCESS.getCode())
                    .message(StatusCodeEnum.SUCCESS.getMessage())
                    .authToken(getAuthToken())
                    .data(myPolicyList)
                    .build();

        } catch (Exception e) {
            return ApiRespDTO.<List<MyPolicyListVO>>builder()
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }
}
