package com.fubon.ecplatformapi.validation;

import com.fubon.ecplatformapi.enums.InsuranceType;
import com.fubon.ecplatformapi.validation.InsTypeValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class InsTypeValidator implements ConstraintValidator<InsTypeValidation, String> {

    @Override
    public void initialize(InsTypeValidation constraintAnnotation) {
    }

    @Override
    public boolean isValid(String insType, ConstraintValidatorContext context) {

        for (InsuranceType type : InsuranceType.values()) {
            if (type.getName().equals(insType)) {
                return true;
            }
        }
        return false;
    }
}
