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

package org.greenbuttonalliance.espi.common.models.atom.adapters;

import org.greenbuttonalliance.espi.common.models.atom.TextType;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static org.junit.Assert.assertEquals;

public class StringAdapterTests {

	private StringAdapter stringAdapter;

	@Before
	public void setup() {
		stringAdapter = new StringAdapter();
	}

	@Test
	public void givenJAXBElement_returnsContent() throws Exception {
		TextType textTypeElement = new TextType();
		textTypeElement.getContent().add("Sample text");
		JAXBElement<TextType> v = new JAXBElement<>(new QName(
				"xmlNamespace", "localPart"), TextType.class, textTypeElement);

		String value = stringAdapter.unmarshal(v);

		assertEquals("Sample text", value);
	}

}
