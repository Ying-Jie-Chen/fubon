package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import com.fubon.ecplatformapi.service.XrefInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class XrefInfoServiceImpl implements XrefInfoService {

    @Override
    public LoginRespVo.ResponseData getXrefInfoList(FubonLoginRespDTO dto){

        List<FubonLoginRespDTO.XrefInfo> xrefInfoList = getXrefInfo(dto.getAny().getXrefInfo());

        return LoginRespVo.ResponseData.builder()
                .userInfo(dto.getAny().getUserInfo())
                .xrefInfo(xrefInfoList)
                .build();
    }

    @Override
    public List<FubonLoginRespDTO.XrefInfo> getXrefInfo(List<FubonLoginRespDTO.XrefInfo> xrefInfoList) {
        return xrefInfoList.stream()
                .map(xrefInfo -> FubonLoginRespDTO.XrefInfo.builder()
                        .xref(xrefInfo.getXref())
                        .ascCrzSale(xrefInfo.getAscCrzSale())
                        .admin(xrefInfo.getAdmin())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public FubonLoginRespDTO.XrefInfo findXrefInfoByXref(List<FubonLoginRespDTO.XrefInfo> xrefInfos, String xref) {
        return xrefInfos.stream()
                .filter(xrefInfo -> xref.equals(xrefInfo.getXref()))
                .findFirst()
                .orElse(null);
    }

}
