package org.energyos.espi.common.domain;

import org.energyos.espi.common.test.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.energyos.espi.common.support.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.common.support.TestUtils.assertSizeValidation;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ThirdPartyTests {
    
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ThirdParty thirdParty = EspiFactory.newThirdParty();

        Set<ConstraintViolation<ThirdParty>> violations = validator.validate(thirdParty);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ThirdParty dataCustodian = new ThirdParty();

        Set<ConstraintViolation<ThirdParty>> violations = validator.validate(dataCustodian);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void id() {
        assertAnnotationPresent(ThirdParty.class, "id", Id.class);
        assertAnnotationPresent(ThirdParty.class, "id", GeneratedValue.class);
    }

    @Test
    public void clientId() {
        assertAnnotationPresent(ThirdParty.class, "clientId", NotEmpty.class);
        assertSizeValidation(ThirdParty.class, "clientId", 2, 64);
    }

    @Test
    public void name() {
        assertAnnotationPresent(ThirdParty.class, "name", NotEmpty.class);
        assertSizeValidation(ThirdParty.class, "name", 2, 64);
    }
}
