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

import static org.energyos.espi.common.test.EspiFactory.newElectricPowerQualitySummary;
import static org.energyos.espi.common.test.EspiFactory.newElectricPowerUsageSummary;
import static org.energyos.espi.common.test.EspiFactory.newLocalTimeParameters;
import static org.energyos.espi.common.test.EspiFactory.newMeterReading;
import static org.energyos.espi.common.test.EspiFactory.newReadingType;
import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.LinkType;
import org.junit.Before;
import org.junit.Test;

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


    @Test
    public void build_givenMeterReading() throws Exception {
        MeterReading meterReading = newMeterReading();
        EntryType entry = new EntryBuilder().build(meterReading);
        assertThat(entry.getContent().getMeterReading(), is(equalTo(meterReading)));
    }

    @Test
    public void build_givenTimeConfiguration() throws Exception {
        TimeConfiguration localTimeParameters = newLocalTimeParameters();
        EntryType entry = new EntryBuilder().build(localTimeParameters);
        assertThat(entry.getContent().getLocalTimeParameters(), is(equalTo(localTimeParameters)));
    }

    @Test
    public void build_givenUsagePoint() throws Exception {
        UsagePoint usagePoint = newUsagePoint();
        EntryType entry = new EntryBuilder().build(usagePoint);
        assertThat(entry.getContent().getUsagePoint(), is(equalTo(usagePoint)));
    }

    @Test
    public void build_givenElectricPowerUsageSummary() throws Exception {
        ElectricPowerUsageSummary electricPowerUsageSummary = newElectricPowerUsageSummary();
        EntryType entry = new EntryBuilder().build(electricPowerUsageSummary);
        assertThat(entry.getContent().getElectricPowerUsageSummary(), is(equalTo(electricPowerUsageSummary)));
    }

    @Test
    public void build_givenElectricPowerQualitySummary() throws Exception {
        ElectricPowerQualitySummary electricPowerQualitySummary = newElectricPowerQualitySummary();
        EntryType entry = new EntryBuilder().build(electricPowerQualitySummary);
        assertThat(entry.getContent().getElectricPowerQualitySummary(), is(equalTo(electricPowerQualitySummary)));
    }

    @Test
    public void build_givenReadingType() throws Exception {
        ReadingType readingType = newReadingType();
        EntryType entry = new EntryBuilder().build(readingType);
        assertThat(entry.getContent().getReadingType(), is(equalTo(readingType)));
    }


}
