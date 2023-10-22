package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.mapper.ResultMapper;
import com.fubon.ecplatformapi.model.dto.req.PolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
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
public class PolicyServiceImpl {

    @Autowired
    ResultMapper resultMapper;

    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;
    @Autowired
    public PolicyServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

//    public PolicyListReq createQueryReq(PolicyListReq policyListReq) {
//        return policyListRepository.save(policyListReq);
//    }


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
                .map(resultMapper::mapToResultVO)
                .collectList()
                .block();
    }


    public DetailResultVo getPolicyDetail(PolicyDetailReqDTO request) {
        // 富邦API - 取得保單資訊
        // API名稱：policyDetail
//        Mono<FubonPolicyDetailRespDTO> mono = webClient
//                .get()
//                .uri("/policyDetail")
//                .accept(MediaType.APPLICATION_JSON)
//                .retrieve()
//                .bodyToMono(new ParameterizedTypeReference<FubonPolicyDetailRespDTO>() {})
//                .log();
//        return  mono
//                .flatMapMany()
//                .map(resultMapper::mapToResultVO)
//                .collectList()
//                .block();

        // 富邦API - 保單寄送紀錄查詢
        // API名稱：getPrnDetail

        // 富邦API - 理賠紀錄查詢
        // 適用險種：全險種
        // API名稱：ClmSalesAppWs/api101
        // 測試API路徑：
        // http://10.0.45.55:9080/fgisws/rest/ClmSalesAppWs/api101

        // 富邦API - 保全紀錄查詢
        // 適用險種：全險種
        // API名稱：chkEnrData
//        List<PolicyDetailReqDTO> detailResp = policyDetailRepository.findByTypeAndNum(insType, policyNum);
//        return detailResp.stream()
//                .map(ModelMapper::mapToDetailResult)
//                .collect(Collectors.toList());
        return null;
    }


}
