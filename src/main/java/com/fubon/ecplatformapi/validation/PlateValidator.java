package com.fubon.ecplatformapi.validation;

import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.model.dto.req.PolicyListReqDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PlateValidator implements ConstraintValidator<PlateValidation, PolicyListReqDTO> {

    @Override
    public void initialize(PlateValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(PolicyListReqDTO request, ConstraintValidatorContext context) {
        if (InsuranceType.MOT.name().equals(request.getInsType())) {
            if (request.getPlate() == null) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("車險種需輸入車牌").addConstraintViolation();
                return false;
            }
        } else {
            if(!request.getPlate().trim().isEmpty()){
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("此險種不需輸入車牌").addConstraintViolation();
                return false;
            }
        }
        return true;
    }
}