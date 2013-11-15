package org.energyos.espi.common.domain;

import org.energyos.espi.common.support.TestUtils;
import org.energyos.espi.common.test.EspiFactory;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.Test;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataCustodianTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        DataCustodian dataCustodian = EspiFactory.newDataCustodian();

        Set<ConstraintViolation<DataCustodian>> violations = validator.validate(dataCustodian);

        assertTrue(violations.isEmpty());
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        DataCustodian dataCustodian = new DataCustodian();

        Set<ConstraintViolation<DataCustodian>> violations = validator.validate(dataCustodian);

        assertFalse(violations.isEmpty());
    }

    @Test
    public void id() {
        TestUtils.assertAnnotationPresent(DataCustodian.class, "id", Id.class);
        TestUtils.assertAnnotationPresent(DataCustodian.class, "id", GeneratedValue.class);
    }

    @Test
    public void clientId() {
        TestUtils.assertColumnAnnotation(DataCustodian.class, "clientId", "client_id");
        TestUtils.assertAnnotationPresent(DataCustodian.class, "clientId", NotEmpty.class);
    }

    @Test
    public void url() {
        TestUtils.assertAnnotationPresent(DataCustodian.class, "url", NotEmpty.class);
    }

    @Test
    public void validations() {
        TestUtils.assertAnnotationPresent(DataCustodian.class, "description", NotEmpty.class);
    }
}
