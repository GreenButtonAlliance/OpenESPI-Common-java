/*
 * Copyright 2013 EnergyOS.org
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

package org.energyos.espi.common.models.atom.adapters;

import static org.junit.Assert.assertTrue;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.energyos.espi.common.models.atom.TextType;
import org.junit.Before;
import org.junit.Test;

public class StringAdapterTests {

    StringAdapter stringAdapter;

    @Before
    public void setup() {
        stringAdapter = new StringAdapter();
    }

    @Test
    public void givenJAXBElement_returnsContent() throws Exception {
        TextType textTypeElement = new TextType();
        textTypeElement.getContent().add("Sample text");
        JAXBElement<TextType> v = new JAXBElement<TextType>(new QName("xmlNamespace", "localPart"), TextType.class, textTypeElement);

        String value = stringAdapter.unmarshal(v);

        assertTrue(value.equals("Sample text"));
    }

}
