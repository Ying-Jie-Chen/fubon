package com.fubon.ecplatformapi.controller.other;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.CreateDetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.model.entity.Token;
import com.fubon.ecplatformapi.repository.TokenRepository;
import com.fubon.ecplatformapi.service.impl.PolicyServiceImpl;
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
    TokenRepository tokenRepository;


    @GetMapping("/queryPolicyList")
    public ApiRespDTO<List<PolicyListResultVO>> queryList(@Valid @RequestBody PolicyListReqDTO req) {

        try {
            log.info("Fubon API /QueryList 的回應結果#Start");
            List<PolicyListResultVO> queryResult = policyService.queryPolicyResults(req);

            Token storedToken = tokenRepository.findLatestToken();

            return ApiRespDTO.<List<PolicyListResultVO>>builder()
                    .authToken(storedToken.getToken())
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
                    .code(StatusCodeEnum.ERR00999.name())
                    .message(StatusCodeEnum.ERR00999.getMessage())
                    .build();
        }
    }


}
