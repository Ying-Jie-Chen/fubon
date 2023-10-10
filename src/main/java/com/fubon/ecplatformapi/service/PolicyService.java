package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.Builber.BuildResponse;
import com.fubon.ecplatformapi.PolicyResultMapper;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.QueryResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {
    @Autowired
    private BuildResponse buildResponse;


    public List<QueryResultVO> getPolicyList() {

        FbQueryResp fbQueryResp = buildResponse.buildQueryResponse();

        List<FbQueryResp.PolicyResult> policyResults = fbQueryResp.getPolicyResults();
        return policyResults.stream()
                .map(PolicyResultMapper::mapToQueryResult)
                .collect(Collectors.toList());
    }
}
