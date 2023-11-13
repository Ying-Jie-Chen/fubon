package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.controller.auth.SessionController;
import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.mapper.PolicyDetailMapper;
import com.fubon.ecplatformapi.mapper.PolicyListMapper;
import com.fubon.ecplatformapi.model.dto.req.*;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.vo.MyPolicyListVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.service.PolicyService;
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

@Slf4j
@Service
public class PolicyServiceImpl extends SessionController implements PolicyService {
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
                .body(BodyInserters.fromValue(req))
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
     * 我的有效保單
     */
    @Override
    public List<MyPolicyListVO> getMyPolicyList(){

        Mono<QueryPolicyListRespDTO> policyListMono = callWebClient("/queryPolicy", getQueryPolicyRequest(), QueryPolicyListRespDTO.class);
        return policyListMono.map(PolicyListMapper::mapToMyPolicyList).block();
    }

    private GetPolicyListReqDTO getQueryPolicyRequest() {
        return GetPolicyListReqDTO.builder()
                .module("POL")
                .ascAdmin(SessionHelper.getValueByAttribute(sessionID(), SessionAttribute.EMP_NO))
                .ascIscXref(SessionHelper.getValueByAttribute(sessionID(), SessionAttribute.XREF_INFOS))
                .fbId(SessionHelper.getValueByAttribute(sessionID(), SessionAttribute.IDENTITY))
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
            Mono<FubonClmSalesRespDTO> clmSalesMono = callWebClient("/ClmSalesAppWs/api101", getClmSalesRequest(request), FubonClmSalesRespDTO.class);
            // 保全紀錄查詢
            Mono<FubonChkEnrDataRespDTO> chkEnrDataMono = callWebClient("/chkEnrData", getChkEnrDataRequest(request), FubonChkEnrDataRespDTO.class);

            return Mono.zip(policyDetailMono, prnDetailMono, clmSalesMono, chkEnrDataMono)
                    .map(tuple -> PolicyDetailMapper.mapToDetailResultVo(request, tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4())).block();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private GetChkEnrDataReqDTO getChkEnrDataRequest(QueryPolicyDetailReqDTO request) {
        return GetChkEnrDataReqDTO.builder()
                .fotmatid(request.getPolicyNum())
                .build();
    }

    private GetClmSalesReqDTO getClmSalesRequest(QueryPolicyDetailReqDTO request) {
        return GetClmSalesReqDTO.builder()
                .query_SalesId("")
                .query_Plan("")
                .query_PolyNo(request.getPolicyNum())
                .build();
    }

    private GetPrnDetailReqDTO getPrnDetailRequest(QueryPolicyDetailReqDTO request) {
        return GetPrnDetailReqDTO.builder()
                .queryType("%")
                .formatid(request.getPolicyNum())
                .cls(request.getInsType())
                .build();
    }

    private GetPolicyDetailReqDTO getPolicyDetailRequest(QueryPolicyDetailReqDTO request) {
        return GetPolicyDetailReqDTO.builder()
                .queryType("1")
                .policyNum(request.getPolicyNum())
                .cls(request.getInsType())
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
