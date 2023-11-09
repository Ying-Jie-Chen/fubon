package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.config.PolicyDetailConfig;
import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.helper.WebClientHelper;
import com.fubon.ecplatformapi.mapper.ResultMapper;
import com.fubon.ecplatformapi.model.dto.req.*;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
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
import reactor.util.function.Tuple4;

import java.util.List;

@Slf4j
@Service
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    PolicyDetailConfig policyDetailConfig;

    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;

    @Autowired
    public PolicyServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

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
                .map(ResultMapper::mapToResultVO)
                .collectList()
                .block();
    }

    @Override
    public DetailResultVo getPolicyDetail(PolicyDetailReqDTO request) {
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
                    .map(tuple -> ResultMapper.mapToDetailResult(tuple.getT1(), tuple.getT2(), tuple.getT3(), tuple.getT4())).block();

        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private GetChkEnrDataReqDTO getChkEnrDataRequest(PolicyDetailReqDTO request) {
        return GetChkEnrDataReqDTO.builder()
                .fotmatid(request.getPolicyNum())
                .build();
    }

    private GetClmSalesReqDTO getClmSalesRequest(PolicyDetailReqDTO request) {
        return GetClmSalesReqDTO.builder()
                .query_SalesId("")
                .query_Plan("")
                .query_PolyNo(request.getPolicyNum())
                .build();
    }

    private GetPrnDetailReqDTO getPrnDetailRequest(PolicyDetailReqDTO request) {
        return GetPrnDetailReqDTO.builder()
                .queryType("%")
                .formatid(request.getPolicyNum())
                .cls(request.getInsType())
                .build();
    }

    private GetPolicyDetailReqDTO getPolicyDetailRequest(PolicyDetailReqDTO request) {
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
