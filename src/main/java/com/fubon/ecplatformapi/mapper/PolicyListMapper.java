package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.model.dto.resp.fubon.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.QueryPolicyListRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.MyPolicyListVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PolicyListMapper {

    /**
     *  保單一般查詢
     */
    public static PolicyListResultVO mapToResultVO(FbQueryRespDTO.PolicyResult policyResult) {
        return new PolicyListResultVO(
                policyResult.getClsGrp(),
                policyResult.getPolFormatid(),
                policyResult.getUnPaidPrm(),
                policyResult.getRmaClinameI(),
                policyResult.getMohPlatno(),
                policyResult.getSecEffdate(),
                policyResult.getSecExpdate()
        );
    }

    /**
     * 我的有效保單
     */
    public static List<MyPolicyListVO> mapToMyPolicyList(QueryPolicyListRespDTO queryPolicyResp){
        List<QueryPolicyListRespDTO.PolicyResult> queryPolicyList = queryPolicyResp.getPolicyResults();

        return queryPolicyList.stream().map(policyResult -> MyPolicyListVO.builder()
                        .policyNum(policyResult.getPolFormatid())
                        .premiums(policyResult.getUnPaidPrm())
                        .insuredName(policyResult.getRmaClinameI())
                        .plate(policyResult.getMohPlatno())
                        .effectDate(policyResult.getSecEffdate().getTime())
                        .expireDate(policyResult.getSecExpdate().getTime())
                        .build())
                .collect(Collectors.toList());

    }
}
