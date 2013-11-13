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

package org.energyos.espi.common.utils.factories;

import org.energyos.espi.common.domain.ServiceCategory;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.FeedType;
import org.energyos.espi.common.models.atom.LinkType;
import org.energyos.espi.common.test.EspiFactory;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertEquals;

public class UsagePointBuilderTests {

    private UsagePointBuilder builder;

    @Before
    public void setup() {
        builder = new UsagePointBuilder();
    }

    @Test
    public void newUsagePoints_id() {
        String title = "Usage Point Title";
        FeedType feed = newFeed(title);
        UsagePoint usagePoint = builder.newUsagePoints(feed).get(0);

        assertEquals("urn:uuid:0071C5A7-91CF-434E-8BCE-C38AC8AF215D", usagePoint.getMRID());
    }

    @Test
    public void newUsagePoints_title() {
        String title = "Usage Point Title";
        FeedType feed = newFeed(title);
        UsagePoint usagePoint = builder.newUsagePoints(feed).get(0);

        assertEquals(title, usagePoint.getDescription());
    }

    @Test
    public void newUsagePoints_givenFeedWithNoEntries_returnsEmptyList() {
        FeedType feed = new FeedType();
        assertEquals(0, builder.newUsagePoints(feed).size());
    }

    @Test
    public void newUsagePoints_givenFeedWithUsagePointEntry_returnsUsagePoint() {
        FeedType feed = newFeed("Usage Point Title");

        assertEquals(UsagePoint.class, builder.newUsagePoints(feed).get(0).getClass());
    }

    @Test
    public void newUsagePoints_givenFeedWithTitledUsagePointEntry_addsEntryTitleToUsagePoint() {
        String title = "Usage Point Title";
        FeedType feed = newFeed(title);

        assertEquals(title, builder.newUsagePoints(feed).get(0).getDescription());
    }

    @Test
    public void newUsagePoint_title() {
        String title = "Single Usage Point Title";
        EntryType entry = newEntry(title);
        newUsagePoint(entry);

        assertEquals(title, builder.newUsagePoint(entry).getDescription());
    }

    @Test
    public void newUsagePoint_givenEntryWithNoContent_returnsNull() {
        EntryType entry = newEntry("Nothing");
        assertNull(builder.newUsagePoint(entry));
    }

    @Test
    public void newUsagePoint_givenEntryWithNonUsagePoint_returnsNull() {
        EntryType entryType = newEntry("Wrong Content");
        ContentType content = new ContentType();
        content.setReadingType(EspiFactory.newReadingType());
        entryType.setContent(content);

        assertNull(builder.newUsagePoint(entryType));
    }

    private EntryType newEntry(String title) {
        EntryType entryType = new EntryType();
        entryType.setTitle(title);
        entryType.setId("urn:uuid:0071C5A7-91CF-434E-8BCE-C38AC8AF215D");

        return entryType;
    }

    private FeedType newFeed(String title) {
        FeedType feed = new FeedType();

        EntryType entryType = newEntry(title);
        newUsagePoint(entryType);
        feed.getEntries().add(entryType);

        return feed;
    }

    private void newUsagePoint(EntryType entryType) {
        ContentType usagePointContentType = new ContentType();
        UsagePoint usagePoint = new UsagePoint();
        usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));
        usagePointContentType.setUsagePoint(usagePoint);
        entryType.setContent(usagePointContentType);
        entryType.getLinks().add(newLinkType("self", "RetailCustomer/9b6c7063/UsagePoint/01"));
        entryType.getLinks().add(newLinkType("related", "RetailCustomer/9b6c7063/UsagePoint/01/MeterReading"));
    }

    private LinkType newLinkType(String rel, String href) {
        LinkType link = new LinkType();
        link.setRel(rel);
        link.setHref(href);

        return link;
    }
}

