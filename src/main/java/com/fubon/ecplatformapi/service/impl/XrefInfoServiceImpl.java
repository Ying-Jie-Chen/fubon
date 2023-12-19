package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.helper.SessionHelper;
import com.fubon.ecplatformapi.mapper.UserInfoMapper;
import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.service.XrefInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Slf4j
@Service
public class XrefInfoServiceImpl implements XrefInfoService {

    @Override
    public LoginRespVo.ResponseData getXrefInfoList(LoginRespDTO dto){

        List<LoginRespDTO.XrefInfo> xrefInfoList = getXrefInfo(dto.getAny().getXrefInfo());
        LoginRespVo.UserInfo userInfo = UserInfoMapper.mapToUserInfoVo(dto);

        return LoginRespVo.ResponseData.builder()
                .userInfo(userInfo)
                .xrefInfo(xrefInfoList)
                .build();
    }

    @Override
    public List<LoginRespDTO.XrefInfo> getXrefInfo(List<LoginRespDTO.XrefInfo> xrefInfoList) {
        return xrefInfoList.stream()
                .map(xrefInfo -> LoginRespDTO.XrefInfo.builder()
                        .xref(xrefInfo.getXref())
                        .ascCrzSale(xrefInfo.getAscCrzSale())
                        .admin(xrefInfo.getAdmin())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public LoginRespDTO.XrefInfo findXrefInfoByXref() {
        String xref = (String) SessionHelper.getValueByAttribute(SessionAttribute.EMP_NO);
        Object xrefInfoList = SessionHelper.getValueByAttribute(SessionAttribute.XREF_INFOS);

        List<LoginRespDTO.XrefInfo> xrefInfos = Optional.ofNullable(xrefInfoList)
                .filter(List.class::isInstance)
                .map(list -> (List<LoginRespDTO.XrefInfo>) list)
                .orElse(Collections.emptyList());

        return xrefInfos.stream()
                .filter(xrefInfo -> {
                    log.info("Xref: " + xrefInfo.getXref());
                    return xref.equals(xrefInfo.getXref());
                })
                .findFirst()
                .orElse(null);
    }

}
