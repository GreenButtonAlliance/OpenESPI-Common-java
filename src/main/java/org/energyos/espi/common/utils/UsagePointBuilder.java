/*
 * Copyright 2013, 2014 EnergyOS.org
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

package org.energyos.espi.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.ContentType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.FeedType;
import org.energyos.espi.common.models.atom.LinkType;
import org.springframework.stereotype.Component;

@Component
public class UsagePointBuilder {

    private EntryLookupTable lookup;
    private List<UsagePoint> usagePoints;

    public List<UsagePoint> newUsagePoints(FeedType feed) {
        usagePoints = new ArrayList<>();
        lookup = new EntryLookupTable(feed.getEntries());

        associate(feed);
        
        return usagePoints;
    }

    public UsagePoint newUsagePoint(EntryType entry) {
        UsagePoint usagePoint = null;

        if (entry.getContent() != null) {
            FeedType feed = new FeedType();
            feed.getEntries().add(entry);
            newUsagePoints(feed);

            usagePoint = entry.getContent().getUsagePoint();
        }

        return usagePoint;
    }

    private void associate(FeedType feed) {
        for (EntryType entry : feed.getEntries()) {
            ContentType content = entry.getContent();
            if (content == null)
                continue;

            if (content.getUsagePoint() != null) {
                handle(entry, content.getUsagePoint());
            } else if (content.getMeterReading() != null) {
                handle(entry, content.getMeterReading());
            } else if (content.getReadingType() != null) {
                handle(entry, content.getReadingType());
            } else if (content.getIntervalBlocks() != null) {
                for (IntervalBlock intervalBlock : content.getIntervalBlocks())
                    handle(entry, intervalBlock);
            } else if (content.getElectricPowerUsageSummary() != null) {
                handle(entry, content.getElectricPowerUsageSummary());
            } else if (content.getElectricPowerQualitySummary() != null) {
                handle(entry, content.getElectricPowerQualitySummary());
            } else if (content.getLocalTimeParameters() != null) {
                handle(entry, content.getLocalTimeParameters());
            }
        }
    }

    private void handle(EntryType entry, UsagePoint usagePoint) {
        updateEntity(usagePoint, entry);

        usagePoint.setLocalTimeParameters(findLocalTimeParameters(entry));

        usagePoints.add(usagePoint);
    }

    private void handle(EntryType entry, MeterReading meterReading) {
        updateEntity(meterReading, entry);

        EntryType usagePointEntry = lookup.getUpEntry(entry);
        usagePointEntry.getContent().getUsagePoint().addMeterReading(meterReading);

        meterReading.setReadingType(findReadingType(entry));
        findReadingType(entry);
    }

    private void handle(EntryType entry, ReadingType readingType) {
        updateEntity(readingType, entry);
    }

    private void handle(EntryType entry, TimeConfiguration timeConfiguration) {
        updateEntity(timeConfiguration, entry);
    }

    private void handle(EntryType entry, IntervalBlock intervalBlock) {
        updateEntity(intervalBlock, entry);

        MeterReading meterReading = lookup.getUpEntry(entry).getContent().getMeterReading();
        meterReading.addIntervalBlock(intervalBlock);
    }

    private void handle(EntryType entry, ElectricPowerUsageSummary electricPowerUsageSummary) {
        updateEntity(electricPowerUsageSummary, entry);

        EntryType usagePointEntry = lookup.getUpEntry(entry);
        usagePointEntry.getContent().getUsagePoint().addElectricPowerUsageSummary(electricPowerUsageSummary);
    }

    private void handle(EntryType entry, ElectricPowerQualitySummary electricPowerQualitySummary) {
        updateEntity(electricPowerQualitySummary, entry);

        EntryType usagePointEntry = lookup.getUpEntry(entry);
        usagePointEntry.getContent().getUsagePoint().addElectricPowerQualitySummary(electricPowerQualitySummary);
    }

    private ReadingType findReadingType(EntryType entry) {
        for (EntryType relatedEntry : lookup.getRelatedEntries(entry)) {
            if (relatedEntry != entry) {
                return relatedEntry.getContent().getReadingType();
            }
        }
        return null;
    }

    private TimeConfiguration findLocalTimeParameters(EntryType entry) {
        for (EntryType relatedEntry : lookup.getRelatedEntries(entry)) {
            if (relatedEntry != entry) {
                return relatedEntry.getContent().getLocalTimeParameters();
            }
        }
        return null;
    }

    private void updateEntity(IdentifiedObject entity, EntryType entry) {
        entity.setMRID(entry.getId());
        entity.setDescription(entry.getTitle());

        entity.setPublished(entry.getPublished().getValue().toGregorianCalendar());
        entity.setUpdated(entry.getUpdated().getValue().toGregorianCalendar());

        entity.setSelfLink(getLinkHrefForType(entry, "self"));
        entity.setUpLink(getLinkHrefForType(entry, "up"));
    }

    private LinkType getLinkHrefForType(EntryType entry, String type) {
        for (LinkType link : entry.getLinks()) {
            if (link.getRel().equals(type)) {
                return link;
            }
        }
        return null;
    }
}
