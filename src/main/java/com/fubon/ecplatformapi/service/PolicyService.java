package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.PolicyResultMapper;
import com.fubon.ecplatformapi.model.dto.resp.FbQueryResp;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVO;
import com.fubon.ecplatformapi.model.dto.vo.ListResultVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyService {


    public List<ListResultVO> getPolicyList(FbQueryResp fbQueryResp) {

        List<FbQueryResp.PolicyResult> policyResults = fbQueryResp.getPolicyResults();
        return policyResults.stream()
                .map(PolicyResultMapper::mapToListResult)
                .collect(Collectors.toList());
    }

    public List<DetailResultVO> getPolicyDetail(String insType, String policyNum) {

        return null;
    }
}
