package org.energyos.espi.common.domain;

import javax.persistence.Embeddable;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class SummaryMeasurementPersistenceTests {
    @Test
    public void persistence() {
        TestUtils.assertAnnotationPresent(SummaryMeasurement.class, Embeddable.class);
    }
}

