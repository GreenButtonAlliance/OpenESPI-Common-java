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
import org.greenbuttonalliance.espi.common.utils.EspiMarshaller;
import org.junit.Test;
import org.xml.sax.SAXException;

import java.io.IOException;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathEvaluatesTo;
import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.greenbuttonalliance.espi.common.test.EspiFactory.newBatchList;

public class BatchListMarshallerTests extends XMLTest {

    public String newXML() throws FeedException {
        BatchList batchList = new BatchList();
        batchList.getResources().add("foo");
        batchList.getResources().add("bar");

        return EspiMarshaller.marshal(batchList);
    }

    @Test
    public void batchList() throws SAXException, IOException, XpathException, FeedException {
        assertXpathExists("/espi:BatchList",
                EspiMarshaller.marshal(newBatchList()));
    }

    @Test
    public void resources() throws SAXException, IOException, XpathException, FeedException {
        assertXpathEvaluatesTo("foo", "/espi:BatchList/espi:resources[1]",
                newXML());
        assertXpathEvaluatesTo("bar", "/espi:BatchList/espi:resources[2]",
                newXML());
    }
}
