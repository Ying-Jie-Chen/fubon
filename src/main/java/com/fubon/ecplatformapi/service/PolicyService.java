package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.PolicyRepository;
import com.fubon.ecplatformapi.PolicyResultMapper;
import com.fubon.ecplatformapi.model.dto.resp.DetailResultDTO;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;
import com.fubon.ecplatformapi.model.entity.QueryReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {

    @Autowired
    private PolicyRepository policyRepository;

    public QueryReq createQueryReq(QueryReq queryReq) {
        return policyRepository.save(queryReq);
    }

    public List<QueryResultVO> getPolicyList(FbQueryResp fbQueryResp) {

        List<FbQueryResp.PolicyResult> policyResults = fbQueryResp.getPolicyResults();

        return policyResults.stream()
                .map(PolicyResultMapper::mapToListResult)
                .collect(Collectors.toList());
    }

    public DetailResultDTO getPolicyDetail(String insType, String policyNum) {
        return null;
//        List<PolicyDetail> detailResp = policyDetailRepository.findByTypeAndNum(insType, policyNum);
//        return detailResp.stream()
//                .map(PolicyResultMapper::mapToDetailResult)
//                .collect(Collectors.toList());
    }
}
