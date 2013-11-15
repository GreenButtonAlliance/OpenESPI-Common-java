package org.energyos.espi.common.domain;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class RetailCustomerPersistenceTests {

    @Test
    public void id() {
        TestUtils.assertAnnotationPresent(RetailCustomer.class, "id", Id.class);
        TestUtils.assertAnnotationPresent(RetailCustomer.class, "id", GeneratedValue.class);
    }
}
