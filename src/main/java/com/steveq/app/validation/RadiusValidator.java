package com.steveq.app.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class RadiusValidator implements ConstraintValidator<RadiusSize, Double>{

    @Override
    public void initialize(RadiusSize constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double aDouble, ConstraintValidatorContext constraintValidatorContext) {
        return aDouble <= 1000 && aDouble >= 100;
    }
}
