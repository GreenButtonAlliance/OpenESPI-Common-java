/*
 * Copyright 2013,2014 EnergyOS.org
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

package org.energyos.espi.common.atom;


import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.support.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.energyos.espi.common.test.EspiFactory.newMeterReadingWithUsagePoint;
import static org.junit.Assert.assertEquals;

public class MeterReadingEntryTests extends XMLTest {

    private MeterReadingEntry entry;

    @Before
    public void before() throws FeedException, SAXException, IOException, XpathException {
        MeterReading meterReading = newMeterReadingWithUsagePoint();
        meterReading.setId(98L);
        meterReading.getUsagePoint().setId(99L);
        meterReading.getUsagePoint().getRetailCustomer().setId(88L);
        meterReading.getReadingType().setId(96L);

        entry = new MeterReadingEntry(meterReading);
    }

    @Test
    public void selfHref() {
        assertEquals("RetailCustomer/88/UsagePoint/99/MeterReading/98", entry.getSelfHref());
    }

    @Test
    public void upHref() {
        assertEquals("RetailCustomer/88/UsagePoint/99/MeterReading", entry.getUpHref());
    }

    @Test
    public void relatedLinks() {
        Assert.assertEquals("RetailCustomer/88/UsagePoint/99/MeterReading/98/IntervalBlock", TestUtils.findRelatedHref(entry, "IntervalBlock"));
        Assert.assertEquals("ReadingType/96", TestUtils.findRelatedHref(entry, "ReadingType"));
    }
}