/*
 *     Copyright (c) 2018-2019 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
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

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.stream.StreamSource;
import java.io.InputStreamReader;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/spring/test-context.xml")
public class BatchListUnmarshallerTest {

	static final String XML_INPUT = ""
			+ "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>"
			+ "<espi:BatchList xmlns:espi=\"http://naesb.org/espi\">"
			+ "   <espi:resource>RetailCustomer/1/UsagePoint/6</espi:resource>"
			+ "</espi:BatchList>";

	@Autowired
	@Qualifier(value = "atomMarshaller")
	private Jaxb2Marshaller marshaller;

	private BatchList batchList;

	@Before
	public void before() {
		batchList = (BatchList) marshaller.unmarshal(new StreamSource(
				new InputStreamReader(IOUtils.toInputStream(XML_INPUT))));
	}

	@Test
	public void BatchList() {
		assertEquals(BatchList.class, batchList.getClass());
		assertEquals(1, batchList.getResources().size());
		assertThat(batchList.getResources().get(0),
				is("RetailCustomer/1/UsagePoint/6"));
	}

}
