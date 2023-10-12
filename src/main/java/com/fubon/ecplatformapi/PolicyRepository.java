package com.fubon.ecplatformapi;

import com.fubon.ecplatformapi.model.dto.resp.FbDetailResp;
import com.fubon.ecplatformapi.model.entity.PolicyDetail;
import com.fubon.ecplatformapi.model.entity.QueryReq;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRepository extends JpaRepository<QueryReq,String> {
    //List<PolicyDetail> findByTypeAndNum(String insType, String policyNum);
}
