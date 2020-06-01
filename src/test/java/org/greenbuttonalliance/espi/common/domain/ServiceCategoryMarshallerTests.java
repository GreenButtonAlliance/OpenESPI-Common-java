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
import org.greenbuttonalliance.espi.common.atom.XMLTest;
import org.greenbuttonalliance.espi.common.test.EspiFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(value = "/spring/test-context.xml")
@ActiveProfiles("devmysql")
public class ServiceCategoryMarshallerTests extends XMLTest {
	@Autowired
	@Qualifier("domainMarshaller")
	private Jaxb2Marshaller jaxb2Marshaller;

	private String newXML() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		jaxb2Marshaller.marshal(EspiFactory.newServiceCategory(),
				new StreamResult(os));
		return os.toString();
	}

	@Test
	public void serviceCategory() throws SAXException, IOException, XpathException {
		assertXpathExists("/espi:ServiceCategory", newXML());
	}

	@Test
	public void kind() throws SAXException, IOException, XpathException {
		assertXpathExists("/espi:ServiceCategory/espi:kind", newXML());
	}
}
