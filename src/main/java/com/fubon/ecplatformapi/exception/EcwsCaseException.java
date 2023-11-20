package com.fubon.ecplatformapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class EcwsCaseException extends NullPointerException{
    private String errorCode;

    public EcwsCaseException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }

}
