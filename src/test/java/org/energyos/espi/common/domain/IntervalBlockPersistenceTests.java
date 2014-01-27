package org.energyos.espi.common.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.energyos.espi.common.support.TestUtils;
import org.hibernate.annotations.LazyCollection;
import org.junit.Test;

public class IntervalBlockPersistenceTests {
    @Test
    public void persistence() {
        TestUtils.assertAnnotationPresent(IntervalBlock.class, Entity.class);
        TestUtils.assertAnnotationPresent(IntervalBlock.class, Table.class);
    }

    @Test
    public void interval() {
        TestUtils.assertAnnotationPresent(IntervalBlock.class, "interval", Embedded.class);
    }

    @Test
    public void intervalReadings() {
        TestUtils.assertAnnotationPresent(IntervalBlock.class, "intervalReadings", OneToMany.class);
        TestUtils.assertAnnotationPresent(IntervalBlock.class, "intervalReadings", LazyCollection.class);
    }

    @Test
    public void meterReading() {
        TestUtils.assertAnnotationPresent(IntervalBlock.class, "meterReading", ManyToOne.class);
        TestUtils.assertAnnotationPresent(IntervalBlock.class, "meterReading", JoinColumn.class);
    }
}
