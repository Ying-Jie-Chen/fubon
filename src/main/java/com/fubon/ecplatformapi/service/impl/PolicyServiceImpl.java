package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.exception.CustomException;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.mapper.PolicyDetailMapper;
import com.fubon.ecplatformapi.mapper.PolicyListMapper;
import com.fubon.ecplatformapi.model.dto.req.*;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.vo.MyPolicyListVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.service.PolicyService;
import com.fubon.ecplatformapi.service.XrefInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    XrefInfoService xrefInfoService;
    private static final String CPG_MOT_URL = "http://10.0.45.55:80/fgisws/rest/EcAppWs";
    private static final String FUBON_API_URL = "http://localhost:8080";
    private static final String URL = "http://10.0.45.55:9080/fgisws/rest/ClmSalesAppWs/api101";
    private final WebClient webClient;

    @Autowired
    public PolicyServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

    /**
     *  保單一般查詢
     */
    @Override
    public List<PolicyListResultVO> queryPolicyResults(PolicyListReqDTO req) {

        Mono<FbQueryRespDTO> mono = webClient
                .post()
                .uri("/queryPolicy")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(getQueryPolicyRequest(req)))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<FbQueryRespDTO>() {
                })
                .log();
        return mono
                .flatMapMany(fbQueryRespDTO -> Flux.fromIterable(fbQueryRespDTO.getPolicyResults()))
                .map(PolicyListMapper::mapToResultVO)
                .collectList()
                .block();
    }

    /**
     * 保單一般查詢 Req
     */
    private GetPolicyListReqDTO getQueryPolicyRequest(PolicyListReqDTO req) {
        return GetPolicyListReqDTO.builder()
                .clsGrp(req.getInsType())
                .module("POL")
                .secFormatid(req.getPolicyNum())
                .rmaClinameI(req.getInsurerName())
                .rmaUidI(req.getInsurerId())
                .rmaClinameA(req.getInsurerName())
                .rmaUidA(req.getInsurerId())
                .mohPlatno(req.getPlate())
                .ascAdmin("")
                .ascIscXref("")
                .fbId("")
                .dateType("qugAdate")
                .dateFr(req.getEffectDateStart())
                .dateTo(req.getEffectDateEnd())
                .sourcePage("import")
                .secWrpsts("PAY")
                .build();
    }

    /**
     * 我的有效保單
     */
    @Override
    public List<MyPolicyListVO> getMyPolicyList(){
        Mono<QueryPolicyListRespDTO> policyListMono = callWebClient("/queryPolicy", getQueryPolicyRequest(), QueryPolicyListRespDTO.class);
        return policyListMono.map(PolicyListMapper::mapToMyPolicyList).block();
    }

    /**
     * 我的有效保單 Req
     *
     */
    private GetPolicyListReqDTO getQueryPolicyRequest() {

        String empNo = (String) SessionHelper.getValueByAttribute(SessionAttribute.EMP_NO);
        String identity = (String) SessionHelper.getValueByAttribute(SessionAttribute.IDENTITY);

        LoginRespDTO.XrefInfo xrefInfo = xrefInfoService.findXrefInfoByXref();
        log.info("XrefInfo: " + xrefInfo + " ,xref: " + (xrefInfo != null ? xrefInfo.getXref() : "N/A"));

        return GetPolicyListReqDTO.builder()
                .clsGrp(null)
                .module("POL")
                .secFormatid(null)
                .rmaClinameI(null)
                .rmaUidI(null)
                .rmaClinameA(null)
                .rmaUidA(null)
                .mohPlatno(null)
                .ascAdmin(empNo)
                .ascIscXref(Optional.ofNullable(xrefInfo).map(LoginRespDTO.XrefInfo::getXref).orElse(null))
                .fbId(identity)
                .dateFr(null)
                .dateTo(null)
                .sourcePage(null)
                .build();
    }

    /**
     * 取得保單資訊
     */
    @Override
    public DetailResultVo getPolicyDetail(QueryPolicyDetailReqDTO request) {
        try {
            // 取得保單資訊
            Mono<FubonPolicyDetailRespDTO> policyDetailMono = callWebClient("/policyDetail", getPolicyDetailRequest(request), FubonPolicyDetailRespDTO.class);
            // 保單寄送紀錄查詢
            Mono<FubonPrnDetailResp> prnDetailMono = callWebClient("/getPrnDetail", getPrnDetailRequest(request), FubonPrnDetailResp.class);
            // 理賠紀錄查詢
            //Mono<FubonClmSalesRespDTO> clmSalesMono = callWebClient("/ClmSalesAppWs/api101", getClmSalesRequest(request), FubonClmSalesRespDTO.class);
            // 保全紀錄查詢
            Mono<FubonChkEnrDataRespDTO> chkEnrDataMono = callWebClient("/chkEnrData", getChkEnrDataRequest(request), FubonChkEnrDataRespDTO.class);

            return Mono.zip(policyDetailMono, prnDetailMono, chkEnrDataMono) //clmSalesMono
                    .map(tuple -> PolicyDetailMapper.mapToDetailResultVo(request, tuple.getT1(), tuple.getT2(),tuple.getT3())).block(); // tuple.getT3(),

        } catch (Exception e){
            throw new CustomException(e.getMessage(), StatusCodeEnum.ERR00999.getCode());
        }

    }

    /**
     * 取得保單資訊 Req
     */
    private GetPolicyDetailReqDTO getPolicyDetailRequest(QueryPolicyDetailReqDTO request) {
        InsuranceType requestedType = InsuranceType.valueOf(request.getInsType());
        String queryType = (InsuranceType.Business_Insurance.contains(requestedType)) ? "4" : "2";

        return GetPolicyDetailReqDTO.builder()
                .queryType(queryType)
                .policyNum(request.getPolicyNum())
                .cls(request.getInsType())
                .build();
    }

    /**
     * 保單寄送紀錄 Req
     */
    private GetPrnDetailReqDTO getPrnDetailRequest(QueryPolicyDetailReqDTO request) {
        return GetPrnDetailReqDTO.builder()
                .queryType("%")
                .formatid(request.getPolicyNum())
                .cls(request.getInsType())
                .build();
    }

    /**
     *  理賠紀錄 Req
     */
    private GetClmSalesReqDTO getClmSalesRequest(QueryPolicyDetailReqDTO request) {
        return GetClmSalesReqDTO.builder()
                .query_SalesId("")
                .query_Plan("")
                .query_PolyNo(request.getPolicyNum())
                .build();
    }

    /**
     *  保全紀錄 Req
     */
    private GetChkEnrDataReqDTO getChkEnrDataRequest(QueryPolicyDetailReqDTO request) {
        return GetChkEnrDataReqDTO.builder()
                .formatid(request.getPolicyNum())
                .build();
    }

    public <T1, T2> Mono<T2> callWebClient(String uri, T1 requestBody, Class<T2> responseClass) {
        return webClient
                .post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(responseClass)
                .log();
    }

}
