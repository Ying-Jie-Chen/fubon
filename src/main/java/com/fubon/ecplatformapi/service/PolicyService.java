package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.req.GetPolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.req.QueryPolicyDetailReqDTO;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;
import com.fubon.ecplatformapi.model.dto.vo.DetailResultVo;
import com.fubon.ecplatformapi.model.dto.vo.MyPolicyListVO;
import com.fubon.ecplatformapi.model.dto.vo.PolicyListResultVO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PolicyService {
    List<PolicyListResultVO> queryPolicyResults(PolicyListReqDTO req);

    List<MyPolicyListVO> getMyPolicyList();

    DetailResultVo getPolicyDetail(QueryPolicyDetailReqDTO request);
}
