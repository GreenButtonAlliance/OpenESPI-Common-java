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

package org.greenbuttonalliance.espi.common.utils;

import com.google.common.collect.Lists;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.greenbuttonalliance.espi.common.domain.IdentifiedObject;
import org.greenbuttonalliance.espi.common.models.atom.ContentType;
import org.greenbuttonalliance.espi.common.models.atom.DateTimeType;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.models.atom.LinkType;
import org.greenbuttonalliance.espi.common.support.EspiFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathNotExists;
import static org.greenbuttonalliance.espi.common.support.EspiFactory.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@ActiveProfiles("devmysql")
public class FragmentMarshallerTests {

	@Autowired
	private Jaxb2Marshaller fragmentMarshaller;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		TestUtils.setupXMLUnit();
	}

	@Test
	public void entry() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry", newXML());
	}

	@Test
	public void entry_id() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:id", newXML());
	}

	@Test
	public void entry_link() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:link", newXML());
	}

	@Test
	public void entry_title() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:title", newXML());
	}

	@Test
	public void entry_published() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:published", newXML());
	}

	@Test
	public void entry_updated() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:updated", newXML());
	}

	@Test
	public void content() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content", newXML());
	}

	@Test
	public void content_empty() throws Exception {
		assertXpathNotExists("/:entry/:content/*", newXML(null));
	}

	@Test
	public void content_UsagePoint() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content/espi:UsagePoint",
				newXML(newUsagePoint()));
	}

	@Test
	public void content_MeterReading() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content/espi:MeterReading",
				newXML(newMeterReading()));
	}

	@Test
	public void content_ReadingType() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content/espi:ReadingType",
				newXML(newReadingType()));
	}

	@Test
	public void content_IntervalBlocks() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content/espi:IntervalBlock",
				newXML(newIntervalBlock()));
	}

	@Test
	public void content_ElectricPowerUsageSummary() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content/espi:ElectricPowerUsageSummary",
				newXML(newElectricPowerUsageSummary()));
	}

	@Test
	public void content_ElectricPowerQualitySummary() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content/espi:ElectricPowerQualitySummary",
				newXML(newElectricPowerQualitySummary()));
	}

	@Test
	public void content_LocalTimeParameters() throws SAXException, IOException, XpathException {
		assertXpathExists("/:entry/:content/espi:LocalTimeParameters",
				newXML(EspiFactory.newLocalTimeParameters()));
	}

	private String newXML() {
		return newXML(newUsagePoint());
	}

	private String newXML(IdentifiedObject resource) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		EntryType entry = new EntryType();
		entry.getLinks().add(new LinkType(LinkType.SELF, "self"));
		entry.setTitle("entry");
		entry.setId("id");
		entry.setPublished(new DateTimeType());
		entry.setUpdated(new DateTimeType());
		ContentType content = new ContentType();
		content.setResources(Lists.newArrayList(resource));
		entry.setContent(content);

		fragmentMarshaller.marshal(entry, new StreamResult(os));

		return os.toString();
	}
}
