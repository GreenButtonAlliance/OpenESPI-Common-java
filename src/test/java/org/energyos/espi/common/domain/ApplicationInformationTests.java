package org.energyos.espi.common.domain;

import org.energyos.espi.common.test.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.energyos.espi.common.support.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.common.support.TestUtils.assertSizeValidation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ApplicationInformationTests {
    
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ApplicationInformation applicationInformation = EspiFactory.newApplicationInformation();

        Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(applicationInformation);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ApplicationInformation applicationInformation = new ApplicationInformation();

        Set<ConstraintViolation<ApplicationInformation>> violations = validator.validate(applicationInformation);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void dataCustodianThirdPartyId() {
        assertAnnotationPresent(ApplicationInformation.class, "dataCustodianThirdPartyId", NotEmpty.class);
        assertSizeValidation(ApplicationInformation.class, "dataCustodianThirdPartyId", 2, 64);
    }

    @Test
    public void thirdPartyApplicationName() {
        assertAnnotationPresent(ApplicationInformation.class, "thirdPartyApplicationName", NotEmpty.class);
        assertSizeValidation(ApplicationInformation.class, "thirdPartyApplicationName", 2, 64);
    }
}
