package org.energyos.espi.common.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class ElectricPowerUsageSummaryPersistenceTests {
    @Test
    public void persistence() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, Entity.class);
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, Table.class);
    }

    @Test
    public void ratchetDemandPeriod() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "ratchetDemandPeriod", Embedded.class);
    }

    @Test
    public void billingPeriod() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "billingPeriod", Embedded.class);
    }

    @Test
    public void currentBillingPeriodOverAllConsumption() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentBillingPeriodOverAllConsumption", Embedded.class);
    }

    @Test
    public void currentDayLastYearNetConsumption() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentDayLastYearNetConsumption", Embedded.class);
    }

    @Test
    public void currentDayNetConsumption() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentDayNetConsumption", Embedded.class);
    }

    @Test
    public void currentDayOverallConsumption() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "currentDayOverallConsumption", Embedded.class);
    }

    @Test
    public void peakDemand() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "peakDemand", Embedded.class);
    }

    @Test
    public void previousDayLastYearOverallConsumption() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "previousDayLastYearOverallConsumption", Embedded.class);
    }

    @Test
    public void previousDayNetConsumption() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "previousDayNetConsumption", Embedded.class);
    }

    @Test
    public void previousDayOverallConsumption() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "previousDayOverallConsumption", Embedded.class);
    }

    @Test
    public void ratchetDemand() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "ratchetDemand", Embedded.class);
    }

    @Test
    public void usagePoint() {
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "usagePoint", ManyToOne.class);
        TestUtils.assertAnnotationPresent(ElectricPowerUsageSummary.class, "usagePoint", JoinColumn.class);
    }
}
