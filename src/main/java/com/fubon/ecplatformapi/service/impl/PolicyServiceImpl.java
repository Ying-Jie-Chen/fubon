package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.mapper.ResultMapper;
import com.fubon.ecplatformapi.model.dto.resp.fb.FubonClmSalesRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fb.FubonPolicyDetailRespDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.fb.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.resp.fb.FubonPrnDetailResp;
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
                .bodyToMono(new ParameterizedTypeReference<FbQueryRespDTO>() {})
                .log();
        return mono
                .flatMapMany(fbQueryRespDTO -> Flux.fromIterable(fbQueryRespDTO.getPolicyResults()))
                .map(ResultMapper::mapToResultVO)
                .collectList()
                .block();
    }

    @Override
    public DetailResultVo getPolicyDetail(PolicyDetailReqDTO request) {

        return Mono.zip(callPolicyDetail(), callPrnDetail(), callClmSales())
                .map(tuple -> {
                    FubonPolicyDetailRespDTO policyDetailResp = tuple.getT1();
                    FubonPrnDetailResp prnDetailResp = tuple.getT2();
                    FubonClmSalesRespDTO clmSalesResp = tuple.getT3();
                    return ResultMapper.mapToDetailResult(policyDetailResp, prnDetailResp, clmSalesResp);
                })
                .block();

//         富邦API - 保全紀錄查詢
//         適用險種：全險種
//         API名稱：chkEnrData
//        List<PolicyDetailReqDTO> detailResp = policyDetailRepository.findByTypeAndNum(insType, policyNum);
//        return detailResp.stream()
//                .map(ModelMapper::mapToDetailResult)
//                .collect(Collectors.toList());
    }

    private Mono<FubonPolicyDetailRespDTO> callPolicyDetail() {
        // 富邦API - 取得保單資訊
        return webClient
                .get()
                .uri("/policyDetail")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FubonPolicyDetailRespDTO.class)
                .log();
    }

    private Mono<FubonPrnDetailResp> callPrnDetail() {
        // 富邦API - 保單寄送紀錄查詢
        return webClient
                .get()
                .uri("/getPrnDetail")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FubonPrnDetailResp.class)
                .log();
    }

    private Mono<FubonClmSalesRespDTO> callClmSales() {
        // 富邦API - 理賠紀錄查詢
        return webClient
                .get()
                .uri("/ClmSalesAppWs/api101")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(FubonClmSalesRespDTO.class)
                .log();
    }



}
