package org.energyos.espi.common.domain;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

public class ElectricPowerQualitySummaryPersistenceTests {
    @Test
    public void persistence() {
        TestUtils.assertAnnotationPresent(ElectricPowerQualitySummary.class, Entity.class);
        TestUtils.assertAnnotationPresent(ElectricPowerQualitySummary.class, Table.class);
    }

    @Test
    public void usagePoint() {
        TestUtils.assertAnnotationPresent(ElectricPowerQualitySummary.class, "usagePoint", ManyToOne.class);
        TestUtils.assertAnnotationPresent(ElectricPowerQualitySummary.class, "usagePoint", JoinColumn.class);
    }
}
