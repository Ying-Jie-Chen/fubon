package com.fubon.ecplatformapi.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomException extends RuntimeException{

    private String errorCode;

    public CustomException(String message, String errorCode){
        super(message);
        this.errorCode = errorCode;
    }

}