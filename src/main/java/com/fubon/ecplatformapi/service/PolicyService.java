package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.repository.PolicyListRepository;
import com.fubon.ecplatformapi.ValidationException;
import com.fubon.ecplatformapi.model.dto.resp.DetailResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Service
public class PolicyService {

    @Autowired
    private PolicyListRepository policyListRepository;

    private static final String FUBON_API_URL = "http://localhost:8080";

    private final WebClient webClient;
    @Autowired
    public PolicyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(FUBON_API_URL).build();
    }

//    public PolicyListReq createQueryReq(PolicyListReq policyListReq) {
//        return policyListRepository.save(policyListReq);
//    }


    public Mono<FbQueryRespDTO> callQueryResp() {
        return webClient
                .get()
                .uri("/queryPolicy")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<FbQueryRespDTO>() {})
                .log();
    }


    public DetailResultDTO getPolicyDetail(String insType, String policyNum) {
        return null;
//        List<PolicyDetail> detailResp = policyDetailRepository.findByTypeAndNum(insType, policyNum);
//        return detailResp.stream()
//                .map(ModelMapper::mapToDetailResult)
//                .collect(Collectors.toList());
    }

    public void isRequestValid(PolicyListReqDTO request) {
        String insType = request.getInsType();
        String plate = request.getPlate();
        Integer queryType = request.getQueryType();

        if (!Arrays.asList("MOT", "CQCCX", "CHCRX", "CTX", "CGX", "FIR", "ENG", "MGO", "CAS").contains(insType)) {
            throw new ValidationException("Invalid insType value");
        }

        if (("MOT".equals(insType) && plate == null) || (Arrays.asList("CQCCX", "CHCRX", "CTX", "CGX", "FIR", "ENG", "MGO", "CAS").contains(insType) && plate != null)) {
            throw new ValidationException("Invalid combination of insType and plate");
        }

        if (queryType == null || (queryType != 0 && queryType != 1)) {
            throw new ValidationException("QueryType parameter must be 0 or 1");
        }
    }

}
