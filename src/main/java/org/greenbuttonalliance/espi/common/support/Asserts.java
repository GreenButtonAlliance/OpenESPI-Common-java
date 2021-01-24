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

package org.greenbuttonalliance.espi.common.support;

import org.custommonkey.xmlunit.XMLUnit;
import org.custommonkey.xmlunit.XpathEngine;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.hamcrest.CoreMatchers;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;

public class Asserts {
	public static void assertXpathValue(String expectedValue,
			String xpathExpression, String inXMLString) throws SAXException,
			IOException, XpathException {
		Document document = XMLUnit.buildControlDocument(inXMLString);
		assertXpathValue(expectedValue, xpathExpression, document);
	}

	public static void assertXpathValue(String expectedValue,
			String xpathExpression, Document inDocument) throws XpathException {
		XpathEngine simpleXpathEngine = XMLUnit.newXpathEngine();
		Assert.hasText(expectedValue,
				simpleXpathEngine.evaluate(xpathExpression, inDocument).trim());

	}

	public static void assertXpathValueStartsWith(String expectedPrefix,
			String xpathExpression, String inXMLString) throws IOException,
			SAXException, XpathException {
		Document xmlDocument = XMLUnit.buildControlDocument(inXMLString);
		XpathEngine simpleXpathEngine = XMLUnit.newXpathEngine();
		String updated = simpleXpathEngine.evaluate(xpathExpression,
				xmlDocument).trim();
		Assert.hasText(updated, String.valueOf(CoreMatchers.startsWith(expectedPrefix)));
	}
}
