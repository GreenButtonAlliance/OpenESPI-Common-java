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
import org.greenbuttonalliance.espi.common.atom.XMLTest;
import org.greenbuttonalliance.espi.common.support.TestUtils;
import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.junit.Test;
import org.xml.sax.SAXException;

import javax.xml.bind.annotation.XmlTransient;
import java.io.IOException;

import static org.greenbuttonalliance.espi.common.test.Asserts.assertXpathValue;
import static org.greenbuttonalliance.espi.common.test.EspiFactory.newIntervalReading;

public class ReadingQualityMarshallerTests extends XMLTest {
	@Test
	public void marshal_setsQuality() throws FeedException, SAXException,
			IOException, XpathException {
		assertXpathValue("100", "espi:IntervalReading/espi:cost",
				EspiMarshaller.marshal(newIntervalReading()));
	}

	@Test
	public void intervalReading_hasTransientAnnotation() {
		TestUtils.assertAnnotationPresent(ReadingQuality.class,
				"intervalReading", XmlTransient.class);
	}
}