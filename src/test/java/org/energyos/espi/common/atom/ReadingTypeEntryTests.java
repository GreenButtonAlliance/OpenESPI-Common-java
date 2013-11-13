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

package org.energyos.espi.common.atom;


import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.domain.ReadingType;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.energyos.espi.common.test.EspiFactory.newReadingType;
import static org.junit.Assert.assertEquals;


public class ReadingTypeEntryTests extends XMLTest {

    private ReadingTypeEntry entry;

    @Before
    public void before() throws FeedException, SAXException, IOException, XpathException {
        ReadingType readingType = newReadingType();
        readingType.setId(96L);

        entry = new ReadingTypeEntry(readingType);
    }

    @Test
    public void selfHref() {
        assertEquals("ReadingType/96", entry.getSelfHref());
    }

    @Test
    public void upHref() {
        assertEquals("ReadingType", entry.getUpHref());
    }
}