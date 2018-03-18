package com.steveq.app.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RadiusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RadiusSize {
    String message() default "Radius must be between 100 and 1000 [m]";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
