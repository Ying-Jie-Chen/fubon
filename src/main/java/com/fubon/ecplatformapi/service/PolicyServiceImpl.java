package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.ResultMapper;
import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.repository.PolicyListRepository;
import com.fubon.ecplatformapi.model.dto.resp.DetailResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
public class PolicyServiceImpl {

    @Autowired
    private PolicyListRepository policyListRepository;
    @Autowired
    private ResultMapper resultMapper;

    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;
    @Autowired
    public PolicyServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

//    public PolicyListReq createQueryReq(PolicyListReq policyListReq) {
//        return policyListRepository.save(policyListReq);
//    }


    public List<PolicyListResultVO> queryPolicyResults() {

        Mono<FbQueryRespDTO> mono = webClient
                .get()
                .uri("/queryPolicy")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<FbQueryRespDTO>() {})
                .log();
        return mono
                .flatMapMany(fbQueryRespDTO -> Flux.fromIterable(fbQueryRespDTO.getPolicyResults()))
                .map(resultMapper::mapToResultVO)
                .collectList()
                .block();
    }


    public DetailResultDTO getPolicyDetail(String insType, String policyNum) {
        return null;
//        List<PolicyDetail> detailResp = policyDetailRepository.findByTypeAndNum(insType, policyNum);
//        return detailResp.stream()
//                .map(ModelMapper::mapToDetailResult)
//                .collect(Collectors.toList());
    }

    public void isRequestValid(PolicyListReqDTO request) {

    }


}
