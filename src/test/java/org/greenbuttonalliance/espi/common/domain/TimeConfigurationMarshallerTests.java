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

import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.junit.Test;

import javax.xml.bind.JAXBException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class TimeConfigurationMarshallerTests {

	static final String XML_INPUT = "<LocalTimeParameters xmlns=\"http://naesb.org/espi\">\n"
			+ "    <dstEndRule>B40E2000</dstEndRule>\n"
			+ "    <dstOffset>3600</dstOffset>\n"
			+ "    <dstStartRule>360E2000</dstStartRule>\n"
			+ "    <tzOffset>-18000</tzOffset>\n" + "</LocalTimeParameters>";

	private TimeConfiguration timeConfiguration() throws JAXBException {
		return EspiMarshaller.<TimeConfiguration> unmarshal(XML_INPUT)
				.getValue();
	}

	@Test
	public void unmarshal_timeConfiguration() throws JAXBException {
		assertEquals(TimeConfiguration.class, timeConfiguration().getClass());
	}

	@Test
	public void unmarshal_setsDstEndRule() throws JAXBException {
		assertArrayEquals(timeConfiguration().getDstEndRule(), new byte[]{-76, 14, 32, 0});
	}

	@Test
	public void unmarshal_setDstOffset() throws JAXBException {
		assertEquals(3600L, timeConfiguration().getDstOffset());
	}

	@Test
	public void unmarshal_setDstStartRule() throws JAXBException {
		assertArrayEquals(timeConfiguration().getDstStartRule(), new byte[]{54, 14, 32, 0});
	}

	@Test
	public void unmarshal_setTzOffset() throws JAXBException {
		assertEquals(-18000L, timeConfiguration().getTzOffset());
	}
}