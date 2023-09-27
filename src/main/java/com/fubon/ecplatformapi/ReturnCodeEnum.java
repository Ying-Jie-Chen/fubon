package com.fubon.ecplatformapi;

import lombok.Getter;

@Getter
public enum ReturnCodeEnum {
    SUCCESS("0000", "Succeed"),
    FAILED("1001", "Failed");

    private final String code;
    private final String msg;

    ReturnCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
