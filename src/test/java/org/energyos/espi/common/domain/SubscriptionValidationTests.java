package org.energyos.espi.common.domain;

import static org.energyos.espi.common.test.EspiFactory.newSubscription;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class SubscriptionValidationTests {
    @Test
    public void isValid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Subscription subscription = newSubscription();

        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);

        assertThat(violations, is(empty()));
    }

    @Test
    public void isInvalid() throws Exception {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

        Subscription subscription = new Subscription();

        Set<ConstraintViolation<Subscription>> violations = validator.validate(subscription);

        assertFalse(violations.isEmpty());
    }
    
    @Test
    public void retailCustomer() {
        TestUtils.assertAnnotationPresent(Subscription.class, "retailCustomer", NotNull.class);
    }
}
