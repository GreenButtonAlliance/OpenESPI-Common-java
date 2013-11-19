package org.energyos.espi.common.utils;
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

import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class EntryBuilderTest {

    private UsagePoint usagePoint;
    private EntryType entryType;

    @Before
    public void setUp() {
        usagePoint = newUsagePoint();
        entryType = new EntryBuilder().build(usagePoint);
    }
    @Test
    public void id() {
        assertThat(entryType.getId(), is("urn:uuid:" + usagePoint.getUUID()));
    }

    @Test
    public void title() {
        assertThat(entryType.getTitle(), is(usagePoint.getDescription()));
    }

    @Test
    public void published() {
        DateTimeType published = entryType.getPublished();
        assertThat(published, is(notNullValue()));
        assertThat(published.getValue().toGregorianCalendar().getTimeInMillis(), is(usagePoint.getPublished().getTimeInMillis()));
    }

    @Test
    public void updated() {
        DateTimeType updated = entryType.getUpdated();
        assertThat(updated, is(notNullValue()));
        assertThat(updated.getValue().toGregorianCalendar().getTimeInMillis(), is(usagePoint.getUpdated().getTimeInMillis()));
    }

    @Test
    public void selfLink() {
        LinkType selfLink = new LinkType();
        selfLink.setHref(usagePoint.getSelfHref());
        selfLink.setRel("self");
        assertThat(entryType.getLinks(), hasItem(selfLink));
    }

    @Test
    public void upLink() {
        LinkType upLink = new LinkType();
        upLink.setHref(usagePoint.getUpHref());
        upLink.setRel("up");
        assertThat(entryType.getLinks(), hasItem(upLink));
    }

    @Test
    public void meterReadingRelatedLinks() {
        List<LinkType> relatedLinks = usagePoint.getRelatedLinks();
        LinkType link = new LinkType("related", usagePoint.getSelfHref() + "/MeterReading");
        assertThat(relatedLinks, hasItem(link));
    }

    @Test
    public void electricPowerUsageSummaryLinks() {
        List<LinkType> relatedLinks = usagePoint.getRelatedLinks();
        LinkType link = new LinkType("related", usagePoint.getSelfHref() + "/ElectricPowerUsageSummary");
        assertThat(relatedLinks, hasItem(link));
    }
    
    @Test
    public void electricPowerQualitySummaryLinks() {
        List<LinkType> relatedLinks = usagePoint.getRelatedLinks();
        LinkType link = new LinkType("related", usagePoint.getSelfHref() + "/ElectricPowerQualitySummary");
        assertThat(relatedLinks, hasItem(link));
    }
}
