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
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.energyos.espi.common.test.EspiFactory.newElectricPowerQualitySummaryWithUsagePoint;
import static org.junit.Assert.assertEquals;

public class ElectricPowerQualitySummaryEntryTests {

    private ElectricPowerQualitySummaryEntry entry;

    @Before
    public void setup() throws JAXBException, FeedException {
        ElectricPowerQualitySummary electricPowerQualitySummary = newElectricPowerQualitySummaryWithUsagePoint();
        electricPowerQualitySummary.setId(1L);
        electricPowerQualitySummary.getUsagePoint().setId(99L);
        electricPowerQualitySummary.getUsagePoint().getRetailCustomer().setId(88L);

        entry = new ElectricPowerQualitySummaryEntry(electricPowerQualitySummary);
    }

    @Test
    public void selfHref() {
        assertEquals("RetailCustomer/88/UsagePoint/99/ElectricPowerQualitySummary/1", entry.getSelfHref());
    }

    @Test
    public void upHref() {
        assertEquals("RetailCustomer/88/UsagePoint/99/ElectricPowerQualitySummary", entry.getUpHref());
    }
}