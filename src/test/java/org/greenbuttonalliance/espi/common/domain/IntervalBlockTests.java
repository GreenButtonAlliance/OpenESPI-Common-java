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

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.greenbuttonalliance.espi.common.models.atom.adapters.IntervalBlockAdapter;
import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.greenbuttonalliance.espi.common.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.greenbuttonalliance.espi.common.support.Asserts.assertXpathValue;
import static org.greenbuttonalliance.espi.common.support.EspiFactory.newIntervalBlockWithUsagePoint;
import static org.junit.Assert.assertEquals;

public class IntervalBlockTests {
	static final String XML_INPUT = "<IntervalBlock xmlns=\"http://naesb.org/espi\">"
			+ "<interval>"
			+ "<duration>3</duration>"
			+ "<start>4</start>"
			+ "</interval>"
			+ "<IntervalReading></IntervalReading>"
			+ "<IntervalReading></IntervalReading>" + "</IntervalBlock>";

	private IntervalBlock intervalBlock;
	private String xml;

	@Before
	public void before() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setupXMLUnit();

		xml = EspiMarshaller.marshal(newIntervalBlockWithUsagePoint());

		IntervalBlockAdapter intervalBlockAdapter = new IntervalBlockAdapter();
		JAXBElement<IntervalBlock> intervalBlockJAXBElement = EspiMarshaller
				.unmarshal(XML_INPUT);
		intervalBlock = intervalBlockAdapter
				.unmarshal(intervalBlockJAXBElement);
	}

	@Test
	public void unmarshallsIntervalBlock() {
		assertEquals(IntervalBlock.class, intervalBlock.getClass());
	}

	@Test
	public void unmarshal_setsInterval() {
		assertEquals(3L, intervalBlock.getInterval().getDuration().longValue());
		assertEquals(4L, intervalBlock.getInterval().getStart().longValue());
	}

	@Test
	public void unmarshal_setsIntervalReadings() {
		assertEquals(intervalBlock, intervalBlock.getIntervalReadings().get(0)
				.getIntervalBlock());
		assertEquals(intervalBlock, intervalBlock.getIntervalReadings().get(1)
				.getIntervalBlock());
	}

	@Test
	public void marshall_setsIntervalDuration() throws SAXException,
			IOException, XpathException {
		assertXpathValue("86400",
				"espi:IntervalBlock/espi:interval/espi:duration", xml);
	}

	@Test
	public void marshall_setsIntervalStart() throws SAXException, IOException,
			XpathException {
		assertXpathValue("1330578000",
				"espi:IntervalBlock/espi:interval/espi:start", xml);
	}

	@Test
	public void marshall_setsIntervalReadings() throws SAXException,
			IOException, XpathException {
		assertXpathExists("espi:IntervalBlock/espi:IntervalReading[1]", xml);
		assertXpathExists("espi:IntervalBlock/espi:IntervalReading[2]", xml);
	}

	@Test
	public void meterReading_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(IntervalBlock.class, "meterReading",
				XmlTransient.class);
	}
}
