package com.fubon.ecplatformapi.exception;

public class TokenValidationException extends Exception {
    public TokenValidationException(String errorMessage) {
        super(errorMessage);
    }
}