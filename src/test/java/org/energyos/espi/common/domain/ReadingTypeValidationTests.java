package org.energyos.espi.common.domain;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

public class ReadingTypeValidationTests {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ReadingType readingType = new ReadingType();
        readingType.setMRID("urn:uuid:E8E75691-7F9D-49F3-8BE2-3A74EBF6BFC0");

        Set<ConstraintViolation<ReadingType>> violations = validator.validate(readingType);

        assertTrue(violations.isEmpty());
    }

}
