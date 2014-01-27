package org.energyos.espi.common.domain;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

import java.util.Set;
import java.util.UUID;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class IdentifiedObjectValidationTests {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        IdentifiedObject identifiedObject = new IdentifiedObject();
        identifiedObject.setUUID(UUID.randomUUID());

        Set<ConstraintViolation<IdentifiedObject>> violations = validator.validate(identifiedObject);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        IdentifiedObject IdentifiedObject = new IdentifiedObject();

        Set<ConstraintViolation<IdentifiedObject>> violations = validator.validate(IdentifiedObject);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void uuid() {
        TestUtils.assertAnnotationPresent(IdentifiedObject.class, "uuid", NotNull.class);
    }
}
