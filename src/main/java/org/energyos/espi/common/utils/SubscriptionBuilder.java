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

package org.energyos.espi.common.utils;

import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.atom.ElectricPowerQualitySummaryEntry;
import org.energyos.espi.common.atom.ElectricPowerUsageSummaryEntry;
import org.energyos.espi.common.atom.IntervalBlocksEntry;
import org.energyos.espi.common.atom.MeterReadingEntry;
import org.energyos.espi.common.atom.ReadingTypeEntry;
import org.energyos.espi.common.atom.TimeConfigurationEntry;
import org.energyos.espi.common.atom.UsagePointEntry;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.springframework.stereotype.Service;

import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.io.FeedException;

@Service
public class SubscriptionBuilder {

    private Feed feed;

    @SuppressWarnings("unchecked")
    public Feed buildFeed(List<UsagePoint> usagePointList) throws FeedException {
        feed = new Feed();

        feed.setFeedType("atom_1.0");
        feed.setId(UUID.randomUUID().toString());
        feed.setTitle("UsagePoint Feed");

        populateEntries(usagePointList);

        return feed;
    }

    private void populateEntries(List<UsagePoint> usagePointList) throws FeedException {
        for (UsagePoint usagePoint : usagePointList) {
            feed.getEntries().add(new UsagePointEntry(usagePoint));

            populateMeterReadingEntries(usagePoint);
            populateElectricPowerUsageSummaryEntries(usagePoint);
            populateElectricPowerQualitySummaryEntries(usagePoint);
            populateTimeConfigurationEntry(usagePoint);
        }
    }

    private void populateTimeConfigurationEntry(UsagePoint usagePoint) throws FeedException {
        TimeConfiguration timeConfiguration = usagePoint.getLocalTimeParameters();
        if (timeConfiguration != null) {
            feed.getEntries().add(new TimeConfigurationEntry(timeConfiguration));
        }
    }

    private void populateElectricPowerQualitySummaryEntries(UsagePoint usagePoint) throws FeedException {
        for (ElectricPowerQualitySummary summary : usagePoint.getElectricPowerQualitySummaries()) {
            feed.getEntries().add(new ElectricPowerQualitySummaryEntry(summary));
        }
    }

    private void populateElectricPowerUsageSummaryEntries(UsagePoint usagePoint) throws FeedException {
        for (ElectricPowerUsageSummary summary : usagePoint.getElectricPowerUsageSummaries()) {
            feed.getEntries().add(new ElectricPowerUsageSummaryEntry(summary));
        }
    }

    private void populateMeterReadingEntries(UsagePoint usagePoint) throws FeedException {
        for (MeterReading meterReading : usagePoint.getMeterReadings()) {
            feed.getEntries().add(new MeterReadingEntry(meterReading));

            populateReadingTypeEntry(meterReading);
            populateIntervalBlocksEntry(meterReading);
        }
    }

    private void populateIntervalBlocksEntry(MeterReading meterReading) throws FeedException {
        if (meterReading.getIntervalBlocks().size() > 0) {
            feed.getEntries().add(new IntervalBlocksEntry(meterReading.getIntervalBlocks()));
        }
    }

    private void populateReadingTypeEntry(MeterReading meterReading) throws FeedException {
        if (meterReading.getReadingType() != null) {
            feed.getEntries().add(new ReadingTypeEntry(meterReading.getReadingType()));
        }
    }
}