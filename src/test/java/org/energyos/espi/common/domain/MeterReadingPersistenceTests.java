package org.energyos.espi.common.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.energyos.espi.common.support.TestUtils;
import org.hibernate.annotations.LazyCollection;
import org.junit.Test;

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
        TestUtils.assertAnnotationPresent(MeterReading.class, "readingType", ManyToOne.class);
        TestUtils.assertAnnotationPresent(MeterReading.class, "readingType", JoinColumn.class);
    }
}
