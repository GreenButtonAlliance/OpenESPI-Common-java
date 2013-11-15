package org.energyos.espi.common.domain;

import org.energyos.espi.common.support.TestUtils;
import org.hibernate.annotations.LazyCollection;
import org.junit.Test;

import javax.persistence.*;

public class MeterReadingPersistenceTests {
    @Test
    public void persistence() {
        TestUtils.assertAnnotationPresent(MeterReading.class, Entity.class);
        TestUtils.assertAnnotationPresent(MeterReading.class, Table.class);
    }

    @Test
    public void intervalBlocks() {
        TestUtils.assertAnnotationPresent(MeterReading.class, "intervalBlocks", OneToMany.class);
        TestUtils.assertAnnotationPresent(MeterReading.class, "intervalBlocks", LazyCollection.class);
    }

    @Test
    public void usagePoint() {
        TestUtils.assertAnnotationPresent(MeterReading.class, "usagePoint", ManyToOne.class);
        TestUtils.assertAnnotationPresent(MeterReading.class, "usagePoint", JoinColumn.class);
    }

    @Test
    public void readingType() {
        TestUtils.assertAnnotationPresent(MeterReading.class, "readingType", OneToOne.class);
        TestUtils.assertAnnotationPresent(MeterReading.class, "readingType", JoinColumn.class);
    }
}
