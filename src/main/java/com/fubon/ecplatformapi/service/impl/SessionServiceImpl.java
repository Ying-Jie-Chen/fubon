package com.fubon.ecplatformapi.service.impl;

import com.fubon.ecplatformapi.enums.SessionAttribute;
import com.fubon.ecplatformapi.model.dto.resp.fubon.FubonLoginRespDTO;
import com.fubon.ecplatformapi.service.SessionService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SessionServiceImpl implements SessionService {

    @Override
    public void setSessionAttributes(HttpSession session, FubonLoginRespDTO.UserInfo user) {
        session.setAttribute(String.valueOf(SessionAttribute.IDENTITY), user.getIdentity());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NO), user.getAgent_id());
        session.setAttribute(String.valueOf(SessionAttribute.EMP_NAME), user.getAgent_name());
        session.setAttribute(String.valueOf(SessionAttribute.FBID), user.getId());
        session.setAttribute(String.valueOf(SessionAttribute.UNION_NUM), user.getUnion_num());
        session.setAttribute(String.valueOf(SessionAttribute.ADMIN_NUM), user.getAdmin_num());
        session.setAttribute(String.valueOf(SessionAttribute.EMAIL), user.getEmail());
        session.setAttribute(String.valueOf(SessionAttribute.SALES_ID), user.getSales_id());
//        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), user.getXrefInfo());
//
//        List<FubonLoginRespDTO.UserInfo.XrefInfo> userXrefInfoList = user.getXrefInfo().stream()
//                .map(xrefInfo -> FubonLoginRespDTO.UserInfo.XrefInfo.builder()
//                        .xref(xrefInfo.getXref())
//                        .channel(xrefInfo.getChannel())
//                        .regno(xrefInfo.getRegno())
//                        .empcname(xrefInfo.getEmpcname())
//                        .adminSeq(xrefInfo.getAdminSeq())
//                        .ascCrzSale(xrefInfo.getAscCrzSale())
//                        .admin(xrefInfo.getAdmin())
//                        .isctype(xrefInfo.getIsctype())
//                        .licempcname(xrefInfo.getLicempcname())
//                        .build())
//                .collect(Collectors.toList());
//
//        session.setAttribute(String.valueOf(SessionAttribute.XREF_INFOS), userXrefInfoList);
    }



    /**
     * 儲存登入者資訊
     *
     */
    @Override
    public void saveSessionInfo(FubonLoginRespDTO fubonLoginRespDTO, HttpSession session){
        FubonLoginRespDTO.UserInfo user = fubonLoginRespDTO.getAny().getUserInfo();
        setSessionAttributes(session, user);
        session.setMaxInactiveInterval(1200);

    }

    @Override
    public void removeSession(HttpSession session){
        log.info("清除Session#Start");

        try {
            for (SessionAttribute attribute : SessionAttribute.values()) {
                session.removeAttribute(attribute.name());
            }
        } catch (Exception e) {
            log.error("Session 中沒有資料" + e.getMessage());
            throw e;
        }

    }


}
