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

import org.greenbuttonalliance.espi.common.support.FixtureFactory;
import org.greenbuttonalliance.espi.common.utils.TestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.StringReader;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
@ActiveProfiles("devmysql")
public class ServiceCategoryUnmarshallerTests {
	@Autowired
	@Qualifier(value = "domainMarshaller")
	private Jaxb2Marshaller jaxb2Marshaller;

	private ServiceCategory loadServiceCategory() throws IOException {
		String xml = FixtureFactory
				.loadFixture("/fixtures/ServiceCategory.xml");
		return (ServiceCategory) jaxb2Marshaller.unmarshal(new StreamSource(
				new StringReader(xml)));
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		TestUtils.setupXMLUnit();
	}

	@Test
	public void kind() throws IOException {
		assertEquals(Long.valueOf(9L), loadServiceCategory().getKind());
	}
}
