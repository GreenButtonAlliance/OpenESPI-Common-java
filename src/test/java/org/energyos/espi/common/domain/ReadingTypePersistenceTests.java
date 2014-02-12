package org.energyos.espi.common.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class ReadingTypePersistenceTests {
    @Test
    public void persistence() {
        TestUtils.assertAnnotationPresent(ReadingType.class, Entity.class);
        TestUtils.assertAnnotationPresent(ReadingType.class, Table.class);
    }

    @Test
    public void interhamonic() {
        TestUtils.assertAnnotationPresent(ReadingType.class, "interharmonic", Embedded.class);
    }

    @Test
    public void argument() {
        TestUtils.assertAnnotationPresent(ReadingType.class, "argument", Embedded.class);
    }
}
