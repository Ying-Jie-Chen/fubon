package com.fubon.ecplatformapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SSOLoginEnum {

    TEST("zAq1xSw2", "cDe3vFr4"),

    PRODUCTION("zaq12wsxcde34rfV", "vfr43edcxsw21qaZ");

    private final String webServiceAcc;
    private final String webServicePwd;
}
