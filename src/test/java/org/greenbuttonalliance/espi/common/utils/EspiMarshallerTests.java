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

import org.custommonkey.xmlunit.XMLAssert;
import org.greenbuttonalliance.espi.common.atom.XMLTest;
import org.greenbuttonalliance.espi.common.domain.ServiceCategory;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.junit.Test;

import static org.greenbuttonalliance.espi.common.test.Asserts.assertXpathValue;

public class EspiMarshallerTests extends XMLTest {

	@Test
	public void marshal_with_marshallableObject_returnsValidXml()
			throws Exception {
		XMLAssert.assertXpathExists("espi:UsagePoint",
				EspiMarshaller.marshal(newUsagePoint()));
	}

	@Test
	public void marshal_with_marshallableObject_returnsXmlWithServiceCategory()
			throws Exception {
		assertXpathValue("1", "espi:UsagePoint/espi:ServiceCategory/espi:kind",
				EspiMarshaller.marshal(newUsagePoint()));
	}

	private UsagePoint newUsagePoint() {
		UsagePoint usagePoint = new UsagePoint();

		ServiceCategory serviceCategory = new ServiceCategory();
		serviceCategory.setKind(1L);

		usagePoint.setServiceCategory(serviceCategory);

		return usagePoint;
	}
}
