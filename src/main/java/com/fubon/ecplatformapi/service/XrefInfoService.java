package com.fubon.ecplatformapi.service;

import com.fubon.ecplatformapi.model.dto.resp.LoginRespDTO;
import com.fubon.ecplatformapi.model.dto.vo.LoginRespVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface XrefInfoService {
    LoginRespVo.ResponseData getXrefInfoList(LoginRespDTO dto);

    List<LoginRespDTO.XrefInfo> getXrefInfo(List<LoginRespDTO.XrefInfo> xrefInfoList);

    LoginRespDTO.XrefInfo findXrefInfoByXref(List<LoginRespDTO.XrefInfo> xrefInfos, String xref);
}
