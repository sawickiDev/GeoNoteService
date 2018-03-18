package com.steveq.app.validation;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RadiusValidator implements ConstraintValidator<RadiusSize, Double>{

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        return aDouble <= 1000 && aDouble >= 100;
    }
}
