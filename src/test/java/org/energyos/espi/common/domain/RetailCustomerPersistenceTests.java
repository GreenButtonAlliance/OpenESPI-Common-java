package org.energyos.espi.common.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class RetailCustomerPersistenceTests {

    @Test
    public void id() {
        TestUtils.assertAnnotationPresent(RetailCustomer.class, "id", Id.class);
        TestUtils.assertAnnotationPresent(RetailCustomer.class, "id", GeneratedValue.class);
    }
}
