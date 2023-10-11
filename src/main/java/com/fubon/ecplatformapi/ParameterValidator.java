package com.fubon.ecplatformapi;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class ParameterValidator implements ConstraintValidator<ParameterValid, String> {
    //private static Set<String> set = new HashSet<>();
    private String[] allowedValues;
    @Override
    public void initialize(ParameterValid constraintAnnotation) {
        //set.addAll(Arrays.asList(constraintAnnotation.values()));
        allowedValues = constraintAnnotation.values();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //return set.contains(value);
        if (value == null) {
            return false;
        }
        for (String allowedValue : allowedValues) {
            if (allowedValue.equals(value)) {
                return true;
            }
        }
//
//        String insType = request.getInsType();
//        boolean isValidInsType = Arrays.asList("MOT", "CQCCX", "CHCRX", "CTX", "CGX", "FIR", "ENG", "MGO", "CAS").contains(insType);
//        if(!isValidInsType){
//            addErrorMessage(context, "Invalid insType value");
//            return false;
//        }
//
//        String plate = request.getPlate();
//        if ("MOT".equals(insType)) {
//            if(plate == null) {
//                addErrorMessage(context, "Plate parameter is required for insType 'MOT'");
//                return false;
//            }
//        }
//
//        Integer queryType = request.getQueryType();
//        if (queryType == null || (queryType != 0 && queryType != 1)) {
//            addErrorMessage(context,"QueryType parameter must be 0 or 1");
//            return false;
//        }
//
        return false;
    }

    private void addErrorMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }
}
