package com.fubon.ecplatformapi.mapper;

import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;

public class UserInfoMapper {

    public static LoginRespVo.UserInfo mapToUserInfoVo(LoginRespDTO loginRespDTO){
        LoginRespDTO.UserInfo userInfo = loginRespDTO.getAny().getUserInfo();
        return LoginRespVo.UserInfo.builder()
                .agentName(userInfo.getAgent_name())
                .agentId(userInfo.getAgent_id())
                .adminNum(userInfo.getAdmin_num())
                .identity(userInfo.getIdentity())
                .email(userInfo.getEmail())
                .unionNum(userInfo.getUnion_num())
                .id(userInfo.getId())
                .signed(userInfo.getSigned())
                .tested2(userInfo.getTested())
                .build();
    }

}
