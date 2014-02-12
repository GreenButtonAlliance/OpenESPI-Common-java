/*
 * Copyright 2013 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.domain;

import static org.energyos.espi.common.test.Asserts.assertXpathValue;

import java.io.IOException;

import javax.xml.bind.annotation.XmlTransient;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.support.TestUtils;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.common.utils.EspiMarshaller;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

public class IntervalReadingMarshallerTests extends XMLTest {

    private String xml;

    @Before
    public void before() throws Exception {
        xml = EspiMarshaller.marshal(EspiFactory.newIntervalReading());
    }

    @Test
    public void intervalBlock_hasTransientAnnotation() {
        TestUtils.assertAnnotationPresent(IntervalReading.class, "intervalBlock", XmlTransient.class);
    }

    @Test
    public void marshal_setsCost() throws SAXException, IOException, XpathException {
        assertXpathValue("100", "espi:IntervalReading/espi:cost", xml);
    }

    @Test
    public void marshal_setsReadingQualities() throws SAXException, IOException, XpathException {
        assertXpathValue("quality1", "espi:IntervalReading/espi:ReadingQuality[1]/espi:quality", xml);
        assertXpathValue("quality2", "espi:IntervalReading/espi:ReadingQuality[2]/espi:quality", xml);
    }

    @Test
    public void marshal_setsTimePeriod() throws SAXException, IOException, XpathException {
        assertXpathValue("86401", "espi:IntervalReading/espi:timePeriod/espi:duration", xml);
        assertXpathValue("1330578001", "espi:IntervalReading/espi:timePeriod/espi:start", xml);
    }

    @Test
    public void marshal_setsValue() throws SAXException, IOException, XpathException {
        assertXpathValue("6", "espi:IntervalReading/espi:value", xml);
    }
}