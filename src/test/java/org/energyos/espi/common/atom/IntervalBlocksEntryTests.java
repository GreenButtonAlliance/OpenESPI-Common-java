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

package org.energyos.espi.common.atom;


import com.sun.syndication.io.FeedException;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

import static org.energyos.espi.common.test.EspiFactory.newMeterReadingWithUsagePoint;
import static org.junit.Assert.assertEquals;

public class IntervalBlocksEntryTests extends XMLTest {

    private IntervalBlocksEntry entry;

    @Before
    public void before() throws JAXBException, FeedException {
        MeterReading meterReading = newMeterReadingWithUsagePoint();
        List<IntervalBlock> intervalBlockList = meterReading.getIntervalBlocks();
        IntervalBlock intervalBlock = intervalBlockList.get(0);
        intervalBlock.setId(97L);
        intervalBlock.getMeterReading().setId(98L);
        intervalBlock.getMeterReading().getUsagePoint().setId(99L);
        intervalBlock.getMeterReading().getUsagePoint().getRetailCustomer().setId(88L);

        entry = new IntervalBlocksEntry(meterReading.getIntervalBlocks());
    }

    @Test
    public void selfHref() {
        assertEquals("RetailCustomer/88/UsagePoint/99/MeterReading/98/IntervalBlock/97", entry.getSelfHref());
    }

    @Test
    public void upHref() {
        assertEquals("RetailCustomer/88/UsagePoint/99/MeterReading/98/IntervalBlock", entry.getUpHref());
    }
}