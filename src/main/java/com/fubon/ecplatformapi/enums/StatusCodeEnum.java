package com.fubon.ecplatformapi.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {

    SUCCESS("00000", "成功"),
    ERR00001("00001", "請求參數錯誤"),
    ERR00002("00002", "授權令牌檢核錯誤"),
    ERR00999("00999", "系統錯誤"),
    ERR02001("02001","查無報價單資料"),
    ERR02002("02002","很抱歉，因駕傷險名冊大於5人無法進行要保書電子簽名，請改以其他方式進行投保！"),
    ERR02003("02003","很抱歉，因家傷險名冊大於8人無法進行要保書電子簽名，請改以其他方式進行投保！"),
    ERR02004("02004","很抱歉，因旅行綜合險被保險人名冊大於10人無法進行要保書電子簽名，請改以其他方式進行投保！"),
    ERR04001("04001","Email名稱錯誤"),
    ERR04002("04002","郵件發送錯誤"),
    ERR04003("04003","行動服務同意書BarCode產生錯誤"),
    ERR04004("04004","行動服務同意書PDF文件產生錯誤"),
    ERR05001("05001","JSON資料格式錯誤"),
    ERR05002("05002","JSON資料處理錯誤");

    private final String code;
    private final String message;

    public static StatusCodeEnum findByCode(String code) {
        for (StatusCodeEnum codeEnum  : StatusCodeEnum.values()) {
            if (codeEnum.getCode().equals(code)){
                return codeEnum;
            }
        }
        return StatusCodeEnum.ERR00999;
    }
}


