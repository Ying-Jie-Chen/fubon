package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.model.dto.PaymentRecordDTO;
import com.fubon.ecplatformapi.model.dto.UnpaidRecordDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.repository.NFNV02Repository;
import com.fubon.ecplatformapi.repository.NFNV03Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.fubon.ecplatformapi.mapper.PolicyDetailSpecificMapper.*;
@Component
public class PolicyDetailMapper {
    private static NFNV02Repository nfnv02Repository;
    private static NFNV03Repository nfnv03Repository;
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
    @Autowired
    public PolicyDetailMapper(NFNV02Repository nfnv02Repository, NFNV03Repository nfnv03Repository) {
        PolicyDetailMapper.nfnv02Repository = nfnv02Repository;
        PolicyDetailMapper.nfnv03Repository = nfnv03Repository;
    }

    public static DetailResultVo mapToDetailResultVo(PolicyDetailReqDTO request, FubonPolicyDetailRespDTO policyDetail, FubonPrnDetailResp prnDetail, FubonClmSalesRespDTO clmSales, FubonChkEnrDataRespDTO chkEnrData) {
        String policyNum = request.getPolicyNum();
        InsuranceType insType = InsuranceType.valueOf(request.getInsType());
        UnpaidRecordDTO unpaidRecord = InsuranceEntityMapper.mapToUnpaidRecordDTO(nfnv02Repository.findUnpaidByPolyno(policyNum));
        PaymentRecordDTO paymentRecord = InsuranceEntityMapper.mapToPaymentRecordDTO(nfnv03Repository.findPaymentByPolyno(policyNum));
        try {
            if (InsuranceType.Car_Insurance.equals(insType) || InsuranceType.Personal_Insurance.contains(insType)) {
                return mapToCarAndPersonalInsType(insType, policyNum, policyDetail, prnDetail, clmSales, chkEnrData, unpaidRecord, paymentRecord);
            } else if(InsuranceType.Business_Insurance.contains(insType)){
                return mapToBusinessInsType(policyDetail, policyNum, clmSales, chkEnrData, unpaidRecord, paymentRecord);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DetailResultVo mapToCarAndPersonalInsType(InsuranceType insType, String policyNum, FubonPolicyDetailRespDTO policyDetail, FubonPrnDetailResp prnDetail, FubonClmSalesRespDTO clmSales, FubonChkEnrDataRespDTO chkEnrData, UnpaidRecordDTO unpaidRecord, PaymentRecordDTO paymentRecord){

        FubonPolicyDetailRespDTO.EcAppInsure ecAppInsure = policyDetail.getEcAppInsure();

        return DetailResultVo.builder()
                //保單基本資料
                .insuranceInfo(mapToInsuranceInfo(insType, ecAppInsure))
                // 保險標的
                .insuranceSubject(mapToEcInsuranceSubject(ecAppInsure))
                // 保險項目
                .insuranceItem(mapToEcInsuranceItem(ecAppInsure))
                // 附加條款
                .additionalTerms(mapToAdditionalTerm(ecAppInsure))
                // 險種名冊
                .insuranceList(mapToInsuranceList(insType, ecAppInsure))
                // 其他險種名冊
                .insuranceOtherList(mapToInsuranceOtherList(ecAppInsure))
                // 保單寄送記錄
                .policyDeliveryRecord(mapToPolicyDeliveryRecord(prnDetail))
                // 未繳保費
                .unpaidRecord(mapToUnpaidRecord(unpaidRecord))
                // 繳費紀錄
                .paidRecord(mapToEcPaidRecord(ecAppInsure, paymentRecord))
                // 理賠紀錄
                .claimRecord(mapToClaimRecord(policyNum, clmSales))
                // 保全紀錄
                .conservationRecord(getConservationRecord(chkEnrData))
                .build();
    }

    private static DetailResultVo mapToBusinessInsType(FubonPolicyDetailRespDTO policyDetail, String policyNum, FubonClmSalesRespDTO clmSales, FubonChkEnrDataRespDTO chkEnrData, UnpaidRecordDTO unpaidRecord, PaymentRecordDTO paymentRecord){

        FubonPolicyDetailRespDTO.EcAppInsureEtp ecAppInsureEtp = policyDetail.getEcAppInsureEtp();

        return DetailResultVo.builder()
                //保單基本資料
                .insuranceInfo(mapToEtpInsuranceInfo(ecAppInsureEtp))
                // 企業險保險標的
                .etpInsuranceSubject(mapToEtpInsuranceSubject(ecAppInsureEtp))
                // 企業險保險標的明細
                .etpInsuranceSubjectDetail(mapToEtpInsuranceSubjectDetail(ecAppInsureEtp))
                // 保險項目
                .insuranceItem(mapToEtpInsuranceItem(ecAppInsureEtp))
                // 未繳保費
                .unpaidRecord(mapToUnpaidRecord(unpaidRecord))
                // 繳費紀錄
                .paidRecord(mapToEtpPaidRecord(paymentRecord))
                // 理賠紀錄
                .claimRecord(mapToClaimRecord(policyNum, clmSales))
                // 保全紀錄
                .conservationRecord(getConservationRecord(chkEnrData))
                .build();
        }


    }