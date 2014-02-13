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

import static org.energyos.espi.common.test.EspiFactory.newUsagePoint;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.models.atom.FeedType;
import org.junit.Test;

public class FeedBuilderTest {

    @Test
    public void build() throws Exception {
        FeedBuilder feedBuilder = new FeedBuilder();
        UsagePoint originalUsagePoint = newUsagePoint();
        List<UsagePoint> usagePointList = new ArrayList<>();
        usagePointList.add(originalUsagePoint);

        FeedType feed = feedBuilder.build(usagePointList);

        assertThat(originalUsagePoint, is(feed.getEntries().get(0).getContent().getUsagePoint()));
    }

    @Test
    public void buildEntry() throws Exception {
        FeedBuilder feedBuilder = new FeedBuilder();
        UsagePoint usagePoint = newUsagePoint();

        EntryType entry = feedBuilder.buildEntry(usagePoint);

        assertThat(entry.getContent().getUsagePoint(), is(usagePoint));
    }
}
