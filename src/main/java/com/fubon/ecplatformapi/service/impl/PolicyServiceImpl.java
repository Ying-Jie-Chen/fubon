package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.config.PolicyDetailConfig;
import com.fubon.ecplatformapi.helper.WebClientHelper;
import com.fubon.ecplatformapi.mapper.ResultMapper;
import com.fubon.ecplatformapi.model.dto.req.GetPolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.fubon.*;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
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
public class PolicyServiceImpl implements PolicyService {
    @Autowired
    PolicyDetailConfig policyDetailConfig;

    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;

    @Autowired
    public PolicyServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

//    public PolicyListReq createQueryReq(PolicyListReq policyListReq) {
//        return policyListRepository.save(policyListReq);
//    }

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

        Mono<FubonPolicyDetailRespDTO> policyDetailMono = callPolicyDetail().cache();
        Mono<FubonPrnDetailResp> prnDetailMono = callPrnDetail().cache();
        Mono<FubonClmSalesRespDTO> clmSalesMono = callClmSales().cache();
        Mono<FubonChkEnrDataRespDTO> chkEnrDataMono = callChkEnrData().cache();



        return Mono.zip(policyDetailMono, prnDetailMono, clmSalesMono, chkEnrDataMono)
                .map(tuple -> {
                    FubonPolicyDetailRespDTO policyDetailResp = tuple.getT1();
                    FubonPrnDetailResp prnDetailResp = tuple.getT2();
                    FubonClmSalesRespDTO clmSalesResp = tuple.getT3();
                    FubonChkEnrDataRespDTO chkEnrDataResp = tuple.getT4();
                    return ResultMapper.mapToDetailResult(policyDetailResp, prnDetailResp, clmSalesResp, chkEnrDataResp);
                })
                .block();
    }

    /**
     * 富邦API - 取得保單資訊
     */
    public Mono<FubonPolicyDetailRespDTO> callPolicyDetail() {
             return webClient
                    .get()
                    .uri("/policyDetail")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(FubonPolicyDetailRespDTO.class)
                    .log();
    }

    /**
     * 富邦API - 保單寄送紀錄查詢
     */
    public Mono<FubonPrnDetailResp> callPrnDetail() {
             return webClient
                    .get()
                    .uri("/getPrnDetail")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(FubonPrnDetailResp.class)
                    .log();
    }

    /**
     * 富邦API - 理賠紀錄查詢
     */
    public Mono<FubonClmSalesRespDTO> callClmSales() {

            return webClient
                    .get()
                    .uri("/ClmSalesAppWs/api101")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(FubonClmSalesRespDTO.class)
                    .log();
    }

    /**
     * 富邦API - 保全紀錄查詢
     */
    public Mono<FubonChkEnrDataRespDTO> callChkEnrData() {
            return webClient
                    .get()
                    .uri("/chkEnrData")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(FubonChkEnrDataRespDTO.class)
                    .log();
    }






}
