/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.domain;

import org.greenbuttonalliance.espi.common.models.atom.adapters.IntervalBlockAdapter;
import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import jakarta.xml.bind.JAXBElement;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;

public class IntervalBlockUnmarshallerTests {

	static final String XML_INPUT = "<IntervalBlock xmlns=\"http://naesb.org/espi\">"
			+ "<interval>"
			+ "<duration>86400</duration>"
			+ "<start>1330578000</start>"
			+ "</interval>"
			+ "<IntervalReading></IntervalReading>"
			+ "<IntervalReading></IntervalReading>" + "</IntervalBlock>";

	private IntervalBlock intervalBlock;

	@Before
	public void before() throws Exception {
		IntervalBlockAdapter intervalBlockAdapter = new IntervalBlockAdapter();
		JAXBElement<IntervalBlock> intervalBlockJAXBElement = EspiMarshaller
				.unmarshal(XML_INPUT);
		intervalBlock = intervalBlockAdapter
				.unmarshal(intervalBlockJAXBElement);
	}

	@Test
	public void unmarshallsIntervalBlock() {
		assertEquals(IntervalBlock.class,
				intervalBlock.getClass());
	}

	@Test
	public void unmarshal_setsIntervalDuration() {
		Assert.assertThat(intervalBlock.getInterval()
						.getDuration(),
				is(86400L));
	}

	@Test
	public void unmarshal_setsIntervalStart() {
		Assert.assertThat(intervalBlock.getInterval().getStart(),
				is(1330578000L));
	}

	@Test
	public void unmarshal_setsIntervalReadings() {
		Assert.assertThat(intervalBlock.getIntervalReadings().get(0).getIntervalBlock(),
				is(intervalBlock));
		Assert.assertThat(intervalBlock.getIntervalReadings().get(1).getIntervalBlock(),
				is(intervalBlock));
	}
}
