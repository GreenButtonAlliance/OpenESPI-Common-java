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

package org.energyos.espi.common.models.atom;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.energyos.espi.common.test.Asserts.assertXpathValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.utils.factories.ATOMFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.xml.sax.SAXException;

import com.sun.syndication.io.FeedException;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration("/spring/test-context.xml")
public class FeedTypeMarshallerTests extends XMLTest {
    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller atomMarshaller;

    private String newXML() throws DatatypeConfigurationException, FeedException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        atomMarshaller.marshal(ATOMFactory.newFeedType(), new StreamResult(os));
        return os.toString();
    }

    @Test
    public void feed() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed", newXML());
    }

    @Test
    public void id() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("urn:uuid:0071C5A7-91CF-434E-8BCE-C38AC8AF215D", "/:feed/:id", newXML());
    }

    @Test
    public void title() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("ThirdPartyX Batch Feed", "/:feed/:title", newXML());
    }

    @Test
    public void updated() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("2012-09-14T00:00:00Z", "/:feed/:updated", newXML());
    }

    @Test
    public void entry() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry", newXML());
    }

    @Test
    public void entry_id() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry", newXML());
    }

    @Test
    public void entry_link() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:link", newXML());
    }

    @Test
    public void entry_title() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:title", newXML());
    }

    @Test
    public void entry_published() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("2012-11-15T00:00:00Z", "/:feed/:entry/:published", newXML());
    }

    @Test
    public void entry_updated() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathValue("2012-10-24T00:00:00Z", "/:feed/:entry/:updated", newXML());
    }

    @Test
    public void content() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:content", newXML());
    }

    @Test
    public void content_UsagePoint() throws SAXException, IOException, XpathException, DatatypeConfigurationException, FeedException {
        assertXpathExists("/:feed/:entry/:content/espi:UsagePoint", newXML());
    }
}
