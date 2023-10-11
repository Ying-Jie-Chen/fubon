package com.fubon.ecplatformapi;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;
@Documented
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ParameterValidator.class)
public @interface ParameterValid {
    String message() default "Parameter validation failed";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String[] values() default {};
}
