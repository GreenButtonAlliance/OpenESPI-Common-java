package org.energyos.espi.common.domain;

import org.energyos.espi.common.support.TestUtils;
import org.hibernate.annotations.LazyCollection;
import org.junit.Test;

import javax.persistence.*;

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
