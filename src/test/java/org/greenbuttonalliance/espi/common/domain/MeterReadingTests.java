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

import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.greenbuttonalliance.espi.common.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.greenbuttonalliance.espi.common.support.EspiFactory.newMeterReadingWithUsagePoint;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class MeterReadingTests {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setupXMLUnit();
	}

	@Test
	public void unmarshalsMeterReading() throws JAXBException {
		final String XML_INPUT = "<MeterReading xmlns=\"http://naesb.org/espi\"/>";

		assertEquals(MeterReading.class, EspiMarshaller.unmarshal(XML_INPUT)
				.getValue().getClass());
	}

	@Test
	public void marshalsMeterReading() throws SAXException, IOException,
			XpathException, FeedException {
		assertXpathExists("espi:MeterReading",
				EspiMarshaller.marshal(newMeterReadingWithUsagePoint()));
	}

	@Test
	public void usagePoint_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(MeterReading.class, "usagePoint",
				XmlTransient.class);
	}

	@Test
	public void intervalBlocks_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(MeterReading.class, "intervalBlocks",
				XmlTransient.class);
	}

	@Test
	public void readingType_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(MeterReading.class, "readingType",
				XmlTransient.class);
	}

	@Test
	public void setUpResource() {
		MeterReading meterReading = new MeterReading();
		UsagePoint usagePoint = new UsagePoint();
		meterReading.setUpResource(usagePoint);

		assertThat(meterReading.getUsagePoint(), equalTo(usagePoint));
	}
}
