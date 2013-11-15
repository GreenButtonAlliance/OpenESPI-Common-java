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


import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.FeedType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.energyos.espi.common.utils.factories.ATOMFactory.newFeedType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class UsagePointBuilderTests {

    UsagePointBuilder builder;

    @Before
    public void before() {
       builder = new UsagePointBuilder();
    }

    @Test
    public void givenFeedWithNoEntries_returnsNull() {
        FeedType feed = new FeedType();
        assertEquals(0, builder.newUsagePoints(feed).size());
    }

    @Test
    public void givenFeedWithUsagePointEntry_returnsUsagePoint() {
        FeedType feed = newFeedType();

        assertEquals(UsagePoint.class, builder.newUsagePoints(feed).get(0).getClass());
    }

    @Test
    public void givenFeedWithTitledUsagePointEntry_addsEntryTitleToUsagePoint() {
        FeedType feed = newFeedType();

        assertEquals("Electric meter", builder.newUsagePoints(feed).get(0).getDescription());
    }

    @Test
    public void setsMRID() {
        FeedType feed = newFeedType();

        assertNotNull(builder.newUsagePoints(feed).get(0).getMRID());
    }
}

