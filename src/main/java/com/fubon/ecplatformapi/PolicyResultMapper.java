package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;

public class PolicyResultMapper {

    public static QueryResultVO mapToQueryResult(FbQueryResp.PolicyResult policyResult){
            return new QueryResultVO (
                    policyResult.getClsGrp(),
                    policyResult.getPolFormatid(),
                    policyResult.getUnPaidPrm(),
                    policyResult.getRmaClinameI(),
                    policyResult.getMohPlatno(),
                    policyResult.getSecEffdate(),
                    policyResult.getSecExpdate());
    }
}
