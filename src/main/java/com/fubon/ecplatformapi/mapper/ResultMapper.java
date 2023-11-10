package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.model.dto.UnpaidRecordDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.model.entity.NFNV02Entity;
import com.fubon.ecplatformapi.repository.CarInsuranceTermRepository;
import com.fubon.ecplatformapi.repository.NFNV02Repository;
import com.fubon.ecplatformapi.repository.NFNV03Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.fubon.ecplatformapi.mapper.PolicyDetailMapper.*;
@Component
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

    private static NFNV02Repository nfnv02Repository;

    @Autowired
    public ResultMapper(NFNV02Repository nfnv02Repository) {
        ResultMapper.nfnv02Repository = nfnv02Repository;
    }

    public static DetailResultVo mapToDetailResultVo(PolicyDetailReqDTO request, FubonPolicyDetailRespDTO policyDetail, FubonPrnDetailResp prnDetail, FubonClmSalesRespDTO clmSales, FubonChkEnrDataRespDTO chkEnrData) {

        FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure = policyDetail.getEcAppInsure();
        FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp = policyDetail.getEcAppInsureEtp();

        String polyNo2 = "Polyno1";
        String polyNo3 = "PolynoC";

        UnpaidRecordDTO unpaidRecord = CarInsuranceMapper.mapToUnpaidRecordDTO(nfnv02Repository.findUnpaidByPolyno(polyNo2));

        try {
            return DetailResultVo.builder()
                    //保單基本資料
                    .insuranceInfo(mapToInsuranceInfo(request, ecAppInsure, ecAppInsureEtp))
                    // 保險標的
                    .insuranceSubject(mapToInsuranceSubject(ecAppInsure))
                    // 企業險保險標的 - 企業險才有
                    .etpInsuranceSubject(mapToEtpInsuranceSubject(ecAppInsureEtp))
                    // 企業險保險標的明細 - 企業險才有
                    .etpInsuranceSubjectDetail(mapToEtpInsuranceSubjectDetail(ecAppInsureEtp))
                    // 保險項目
                    .insuranceItem(mapToInsuranceItem(ecAppInsure, ecAppInsureEtp))
                    // 附加條款 - 車險才有
                    .additionalTerms(mapToAdditionalTerm(ecAppInsure))
                    // 險種名冊 - 車險才有
                    .insuranceList(mapToInsuranceList(ecAppInsure))
                    // 其他險種名冊
                    .insuranceOtherList(mapToInsuranceOtherList(ecAppInsure))
                    // 保單寄送記錄
                    .policyDeliveryRecord(mapToPolicyDeliveryRecord(prnDetail))
                    // 未繳保費
                    .unpaidRecord(mapToUnpaidRecord(unpaidRecord))
                    // 繳費紀錄
                    .paidRecord(mapToPaidRecord(polyNo3))
                    // 理賠紀錄
                    .claimRecord(mapToClaimRecord(clmSales))
                    // 保全紀錄
                    .conservationRecord(getConservationRecord(chkEnrData))
                    .build();
        }catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }



    public DetailResultVo mapToDetailResultVoForCarInsurance(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp, String polyNo2, String polyNo3) {
        //DetailResultVo resultVo = mapToDetailResultVo(ecAppInsure, ecAppInsureEtp);
        // 如果有車險特有的映射邏輯，可以在這裡添加
        //return resultVo;
        return null;
    }

    public DetailResultVo mapToDetailResultVoForPersonalInsurance(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp, String polyNo2, String polyNo3) {
        //DetailResultVo resultVo = mapToDetailResultVo(ecAppInsure, ecAppInsureEtp);
        // 如果有個險特有的映射邏輯，可以在這裡添加
        //return resultVo;
        return null;
    }

    public DetailResultVo mapToDetailResultVoForBusinessInsurance(FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure, FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp, String polyNo2, String polyNo3) {
        //DetailResultVo resultVo = mapToDetailResultVo(ecAppInsure, ecAppInsureEtp);
        // 如果有企業險特有的映射邏輯，可以在這裡添加
        //return resultVo;
        return null;
    }

}