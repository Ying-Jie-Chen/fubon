package com.fubon.ecplatformapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {
    Err10001("系統錯誤");

    private final String message;

    public static StatusCodeEnum findByCode(String code) {
        for (StatusCodeEnum e  : StatusCodeEnum.values()) {
            if (e.name().equals(code)) {
                return e;
            }
        }
        return Err10001;
    }

//    SUCCESS("0000", "Succeed"),
//    FAILED("1001", "Failed");
//
//    private final String code;
//    private final String msg;
//
//    StatusCodeEnum(String code, String msg) {
//        this.code = code;
//        this.msg = msg;
//    }
}
