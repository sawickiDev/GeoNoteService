package com.steveq.app.validation;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Spy;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.*;

public class RadiusValidatorTest {

    @Mock
    private ConstraintValidatorContext cvc;

    @Spy
    private RadiusValidator radiusValidator = new RadiusValidator();

    @Test
    public void isInValidPositive() {
        Assert.assertEquals(false, radiusValidator.isValid(2000.0, cvc));
    }

    @Test
    public void isInValidNegative() {
        Assert.assertEquals(false, radiusValidator.isValid(-100.0, cvc));
    }

    @Test
    public void isValid() {
        Assert.assertEquals(true, radiusValidator.isValid(200.0, cvc));
    }
}