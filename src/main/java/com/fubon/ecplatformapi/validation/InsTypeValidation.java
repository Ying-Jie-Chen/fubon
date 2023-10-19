package com.fubon.ecplatformapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = InsTypeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InsTypeValidation {
    String message() default "險種不符合要求";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
