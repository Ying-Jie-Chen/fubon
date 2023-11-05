package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;

import java.util.*;

import static com.fubon.ecplatformapi.mapper.PolicyDetailMapper.*;

public class ResultMapper {

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

    public static DetailResultVo mapToDetailResult(FubonPolicyDetailRespDTO policyDetail, FubonPrnDetailResp prnDetail, FubonClmSalesRespDTO clmSales) {

        FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure = policyDetail.getEcAppInsure();
        FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp = policyDetail.getEcAppInsureEtp();

        String polyNo = null;

        return DetailResultVo.builder()
                //保單基本資料
                .insuranceInfo(mapToInsuranceInfo(ecAppInsure, ecAppInsureEtp))
                // 保險標的
                .insuranceSubject(mapToInsuranceSubject(ecAppInsure))
                // 企業險保險標的
                .etpInsuranceSubject(Collections.singletonList(mapToEtpInsuranceSubject(ecAppInsureEtp)))
                // 企業險保險標的明細
                .etpInsuranceSubjectDetail(Collections.singletonList(mapToEtpInsuranceSubjectDetail(ecAppInsureEtp)))
                // 保險項目
                .insuranceItem(Collections.singletonList(mapToInsuranceItem(ecAppInsure, ecAppInsureEtp)))
                // 險種名冊
                .insuranceList(Collections.singletonList(mapToInsuranceList(ecAppInsure)))
                // 其他險種名冊
                .insuranceOtherList(Collections.singletonList(mapToInsuranceOtherList(ecAppInsure)))
                // 保單寄送記錄
                .policyDeliveryRecord(Collections.singletonList(mapToPolicyDeliveryRecord(prnDetail)))
                // 未繳保費
                .unpaidRecord(Collections.singletonList(mapToUnpaidRecord(polyNo)))
                // 繳費紀錄
                .paidRecord(Collections.singletonList(mapToPaidRecord(polyNo)))
                // 理賠紀錄
                .claimRecord(mapToClaimRecord(clmSales))
                // 保全紀錄
                //.conservationRecord(Collections.singletonList(getConservationRecord()))
                .build();
    }
}