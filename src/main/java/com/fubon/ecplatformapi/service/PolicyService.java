package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.resp.FbQueryRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import com.fubon.ecplatformapi.repository.PolicyListRepository;
import com.fubon.ecplatformapi.ValidationException;
import com.fubon.ecplatformapi.model.dto.resp.DetailResultDTO;
import com.fubon.ecplatformapi.model.entity.PolicyListReq;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PolicyService {

    @Autowired
    private PolicyListRepository policyListRepository;
    @Autowired
    private ModelMapper modelMapper;

//    public PolicyListReq createQueryReq(PolicyListReq policyListReq) {
//        return policyListRepository.save(policyListReq);
//    }


//    public ListResultDTO callQueryResp() {
//        Mono<ListResultDTO> mono = WebClient.create()
//                .get()
//                .uri("http://localhost:8080/queryPolicy")
//                .retrieve()
//                .bodyToMono(ListResultDTO.class);
//        return mono.block();
//    }

    public Mono<List<FbQueryRespDTO>> callQueryResp() {

        return WebClient.create()
                .get()
                .uri("http://localhost:8080/queryPolicy")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<FbQueryRespDTO>>() {})
                .log();
    }

    public DetailResultDTO getPolicyDetail(String insType, String policyNum) {
        return null;
//        List<PolicyDetail> detailResp = policyDetailRepository.findByTypeAndNum(insType, policyNum);
//        return detailResp.stream()
//                .map(PolicyResultMapper::mapToDetailResult)
//                .collect(Collectors.toList());
    }

    public boolean isRequestValid(PolicyListReq request) {
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
        return true;
    }

}
