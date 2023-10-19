package com.fubon.ecplatformapi.exception;

import com.fubon.ecplatformapi.enums.StatusCodeEnum;
import com.fubon.ecplatformapi.model.dto.resp.ApiRespDTO;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class RequestParamExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiRespDTO handlerValidationErrors(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return ApiRespDTO
                .builder()
                .code(StatusCodeEnum.Err10001.name())
                .message(errors.toString())
                .build();

    }
}
