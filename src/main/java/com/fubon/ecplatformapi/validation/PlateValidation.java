package com.fubon.ecplatformapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PlateValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PlateValidation {
    String message() default "車牌不符合要求";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
