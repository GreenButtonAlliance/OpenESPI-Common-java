/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.support.TestUtils;
import org.junit.Test;

public class UsagePointMarshallingTests extends XMLTest {
	@Test
	public void roleFlags() {
		TestUtils.assertAnnotationPresent(UsagePoint.class, "roleFlags",
				XmlElement.class);
	}

	@Test
	public void serviceCategory() {
		TestUtils.assertAnnotationPresent(UsagePoint.class, "serviceCategory",
				XmlElement.class);
	}

	@Test
	public void meterReadings() {
		TestUtils.assertAnnotationPresent(UsagePoint.class, "meterReadings",
				XmlTransient.class);
	}

	@Test
	public void electricPowerQualitySummaries() {
		TestUtils.assertAnnotationPresent(UsagePoint.class,
				"electricPowerQualitySummaries", XmlTransient.class);
	}

	@Test
	public void electricPowerUsageSummaries() {
		TestUtils.assertAnnotationPresent(UsagePoint.class,
				"electricPowerUsageSummaries", XmlTransient.class);
	}

	@Test
	public void localTimeParameters() {
		TestUtils.assertAnnotationPresent(UsagePoint.class,
				"localTimeParameters", XmlTransient.class);
	}

	@Test
	public void retailCustomer() {
		TestUtils.assertAnnotationPresent(UsagePoint.class, "retailCustomer",
				XmlTransient.class);
	}

}
