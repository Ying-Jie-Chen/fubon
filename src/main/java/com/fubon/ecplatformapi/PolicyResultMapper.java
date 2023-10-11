package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.resp.FbDetailResp;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.ListResultVO;

public class PolicyResultMapper {

    public static ListResultVO mapToListResult(FbQueryResp.PolicyResult policyResult){
            return new ListResultVO(
                    policyResult.getClsGrp(),
                    policyResult.getPolFormatid(),
                    policyResult.getUnPaidPrm(),
                    policyResult.getRmaClinameI(),
                    policyResult.getMohPlatno(),
                    policyResult.getSecEffdate(),
                    policyResult.getSecExpdate());
    }

    public static DetailResultVO.BasicInfo mapToDetailResult(FbDetailResp.EcAppInsure ecAppInsure){
        return new DetailResultVO.BasicInfo(
                //ecAppInsure.getSecEcAppWsBean().getSecFormatid(),
                ecAppInsure.getSecEcAppWsBean().getSecEffdate(),
                ecAppInsure.getSecEcAppWsBean().getSecExpdate(),
                ecAppInsure.getSecEcAppWsBean().getSecAeffdate(),
                ecAppInsure.getSecEcAppWsBean().getSecEffdate(),
                ecAppInsure.getTotalPremium(),
                ecAppInsure.getPolSt,
                ecAppInsure.getVoltPremium,
                ecAppInsure.getCompPremium,
                ecAppInsure.getSecEcAppWsBean.getSecEip,
                ecAppInsure.getSecEcAppWsBean.getSecFubonlifeAgree,
                ecAppInsureEtp.getCgrCode,
                ecAppInsureEtp.getClsCode,
                ecAppInsureEtp.getPlnCode,
                ecAppInsureEtp.getTotalPremium100,
                ecAppInsureEtp.getOurshr,
                ecAppInsure.getEcoEcAppWsBean.getEcoPtcptno);
    }

}
