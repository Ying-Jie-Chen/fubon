package com.fubon.ecplatformapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SessionAttribute {

    IDENTITY("identity"), EMP_NO("empNo"), EMP_NAME("empName"),

    FBID("fbid"), UNION_NUM("unionNum"), ADMIN_NUM("adminNum"),

    EMAIL("email"), SALES_ID("salesId"), XREF_INFOS("xrefInfos");

    private final String attributeName;
}
