package org.energyos.espi.common.domain;

import javax.xml.bind.annotation.XmlTransient;

import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class MeterReadingMarshallingTests {
    @Test
    public void intervalBlocks() {
        TestUtils.assertAnnotationPresent(MeterReading.class, "intervalBlocks", XmlTransient.class);
    }

    @Test
    public void usagePoint() {
        TestUtils.assertAnnotationPresent(MeterReading.class, "usagePoint", XmlTransient.class);
    }

    @Test
    public void readingType() {
        TestUtils.assertAnnotationPresent(MeterReading.class, "readingType", XmlTransient.class);
    }
}
