/*
 *    Copyright (c) 2018-2020 Green Button Alliance, Inc.
 *
 *    Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.models.atom.LinkType;
import org.greenbuttonalliance.espi.common.utils.TestUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.greenbuttonalliance.espi.common.support.EspiFactory.newUsagePoint;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.MatcherAssert.assertThat;

public class UsagePointTests {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setupXMLUnit();
	}

	@Test
	public void getRelatedLinks() {
		UsagePoint usagePoint = newUsagePoint();
		LinkType electricPowerQualitySummaryLink = new LinkType();
		electricPowerQualitySummaryLink.setHref(usagePoint.getSelfHref()
				+ "/ElectricPowerQualitySummary");
		electricPowerQualitySummaryLink.setRel("related");
		usagePoint.getRelatedLinks().add(electricPowerQualitySummaryLink);

		LinkType electricPowerUsageSummaryLink = new LinkType();
		electricPowerUsageSummaryLink.setHref(usagePoint.getSelfHref()
				+ "/ElectricPowerUsageSummary");
		electricPowerUsageSummaryLink.setRel("related");
		usagePoint.getRelatedLinks().add(electricPowerUsageSummaryLink);

		LinkType usageSummaryLink = new LinkType();
		usageSummaryLink.setHref(usagePoint.getSelfHref()
				+ "/UsageSummary");
		usageSummaryLink.setRel("related");
		usagePoint.getRelatedLinks().add(usageSummaryLink);

		LinkType meterReadingLink = new LinkType();
		meterReadingLink.setHref(usagePoint.getSelfHref()
				+ "/ElectricPowerUsageSummary");
		meterReadingLink.setRel("related");
		usagePoint.getRelatedLinks().add(meterReadingLink);

		assertThat(usagePoint.getRelatedLinks(),
				hasItem(electricPowerQualitySummaryLink));
		assertThat(usagePoint.getRelatedLinks(),
				hasItem(electricPowerUsageSummaryLink));
		assertThat(usagePoint.getRelatedLinks(),
				hasItem(meterReadingLink));
		assertThat(usagePoint.getRelatedLinks(),
				hasItem(usageSummaryLink));
	}

	@Test
	public void getRelatedLinkHrefs() throws Exception {
		UsagePoint usagePoint = new UsagePoint();
		LinkType link1 = new LinkType();
		link1.setHref("href1");
		usagePoint.getRelatedLinks().add(link1);
		LinkType link2 = new LinkType();
		link2.setHref("href2");
		usagePoint.getRelatedLinks().add(link2);

		List<String> relatedLinkHrefs = usagePoint.getRelatedLinkHrefs();

		assertThat(relatedLinkHrefs,
				allOf(Matchers.hasItem("href1"), Matchers.hasItem("href2")));
	}
}
