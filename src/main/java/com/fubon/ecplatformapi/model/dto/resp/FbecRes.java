package com.fubon.ecplatformapi.model.dto.resp;

import com.fubon.ecplatformapi.model.entity.UserInfo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FbecRes {
    private boolean staffValid;
    private String staffValidMsg;
    private  UserInfo userInfo;

}

