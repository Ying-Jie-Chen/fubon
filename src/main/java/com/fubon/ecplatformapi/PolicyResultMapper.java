package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO.*;
@Mapper
public interface PolicyResultMapper {

//    public static PolicyListResultVO mapToListResult(FbQueryRespDTO policyResult){
//        return new PolicyListResultVO(
//                policyResult.getClsGrp(),
//                policyResult.getPolFormatid(),
//                policyResult.getUnPaidPrm(),
//                policyResult.getRmaClinameI(),
//                policyResult.getMohPlatno(),
//                policyResult.getSecEffdate(),
//                policyResult.getSecExpdate()
//        );
//
//    }


//    public static CreateDetailResultVO mapToDetailResult(FbDetailResp fbDetailResp) {
//        FbDetailResp.EcAppInsure ecAppInsure = fbDetailResp.getEcAppInsure();
//        FbDetailResp.EcAppInsureEtp ecAppInsureEtp = fbDetailResp.getEcAppInsureEtp();
//
//        DetailResult.InsuredInfo insuredInfo = new DetailResult.InsuredInfo(
//                ecAppInsure.getRmalEcAppWsBean().getRmaCliname(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaUid(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaPBirth(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaAddr(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaTel1(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaTel2(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaEmail(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaMobTel(),
//                null, null, null,
//                null, null,
//                ecAppInsure.getRmalEcAppWsBean().getRmaRepresentative(),
//                ecAppInsure.getRmalEcAppWsBean().getRmaRepresentativeId()
//
//        );

        //DetailResult.BasicInfo basicInfo = new DetailResult.BasicInfo(
//        InsuranceInfo.BasicInfo basicInfo = new InsuranceInfo.BasicInfo(
//                ecAppInsure.getSecEcAppWsBean().getSecFormatid(),
//                ecAppInsure.getSecEcAppWsBean().getSecEffdate(),
//                ecAppInsure.getSecEcAppWsBean().getSecExpdate(),
//                ecAppInsure.getSecEcAppWsBean().getSecAeffdate(),
//                ecAppInsure.getSecEcAppWsBean().getSecEffdate(),
//                ecAppInsure.getTotalPremium(),
//                ecAppInsure.getPolSt(),
//                ecAppInsure.getVoltPremium(),
//                ecAppInsure.getCompPremium(),
//                ecAppInsure.getSecEcAppWsBean().getSecEip(),
//                ecAppInsure.getSecEcAppWsBean().getSecFubonlifeAgree(),
//                ecAppInsureEtp.getCgrCode(),
//                ecAppInsureEtp.getClsCode(),
//                ecAppInsureEtp.getPlnCode(),
//                ecAppInsureEtp.getTotalPremium100(),
//                ecAppInsureEtp.getOurshr(),
//                ecAppInsure.getEcoEcAppWsBean().getEcoPtcptno()
//        );

//        CreateDetailResultVO resultVO = new CreateDetailResultVO();
//        resultVO.getDetailResult().getInsuranceInfo().setBasicInfo(basicInfo);
//        //resultVO.getDetailResult().getInsuranceInfo().setInsuredInfo(insuredInfo);
//        return resultVO;
//    }
}
