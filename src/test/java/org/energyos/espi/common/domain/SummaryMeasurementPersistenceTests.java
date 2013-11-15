package org.energyos.espi.common.domain;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

import javax.persistence.Embeddable;

public class SummaryMeasurementPersistenceTests {
    @Test
    public void persistence() {
        TestUtils.assertAnnotationPresent(SummaryMeasurement.class, Embeddable.class);
    }
}

