package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface XrefInfoService {
    LoginRespVo.ResponseData getXrefInfoList(FubonLoginRespDTO dto);

    List<FubonLoginRespDTO.XrefInfo> getXrefInfo(List<FubonLoginRespDTO.XrefInfo> xrefInfoList);

    FubonLoginRespDTO.XrefInfo findXrefInfoByXref(List<FubonLoginRespDTO.XrefInfo> xrefInfos, String xref);
}
