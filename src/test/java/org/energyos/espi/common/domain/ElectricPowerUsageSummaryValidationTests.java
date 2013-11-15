package org.energyos.espi.common.domain;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.energyos.espi.common.test.EspiFactory.newElectricPowerUsageSummary;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

public class ElectricPowerUsageSummaryValidationTests {

    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        ElectricPowerUsageSummary electricPowerUsageSummary = newElectricPowerUsageSummary();

        Set<ConstraintViolation<ElectricPowerUsageSummary>> violations = validator.validate(electricPowerUsageSummary);

        assertThat(violations, is(empty()));
    }
}
