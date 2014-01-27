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

package org.energyos.espi.common.domain;

import static org.custommonkey.xmlunit.XMLAssert.assertXpathExists;
import static org.energyos.espi.common.test.Asserts.assertXpathValue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.transform.stream.StreamResult;

import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.support.TestUtils;
import org.energyos.espi.common.test.EspiFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class UsagePointMarshallerTests extends XMLTest {
    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller atomMarshaller;

    private String newXML() throws DatatypeConfigurationException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        atomMarshaller.marshal(EspiFactory.newUsagePoint(), new StreamResult(os));
        return os.toString();
    }

    @Test
    public void UsagePoint() throws SAXException, IOException, XpathException, DatatypeConfigurationException {
        assertXpathExists("/espi:UsagePoint", newXML());
    }

    @Test
    public void roleFlags() throws SAXException, IOException, XpathException, DatatypeConfigurationException {
        assertXpathValue("726F6C6520666C616773", "/espi:UsagePoint/espi:roleFlags", newXML());
    }

    @Test
    public void serviceCategory() throws SAXException, IOException, XpathException, DatatypeConfigurationException {
        assertXpathExists("/espi:UsagePoint/espi:ServiceCategory", newXML());
    }

    @Test
    public void status() throws SAXException, IOException, XpathException, DatatypeConfigurationException {
        assertXpathValue("5", "espi:UsagePoint/espi:status", newXML());
    }

    @Test
    public void ServiceDeliveryPoint() throws SAXException, IOException, XpathException, DatatypeConfigurationException {
        assertXpathExists("/espi:UsagePoint/espi:ServiceDeliveryPoint", newXML());
    }

    @Test
    public void ServiceCategory_kind() throws SAXException, IOException, XpathException, DatatypeConfigurationException {
        assertXpathValue("0", "espi:UsagePoint/espi:ServiceCategory/espi:kind", newXML());
    }

    @Test
    public void meterReadings_shouldNotBeMarshaled() {
        TestUtils.assertFieldNotMarshallable(UsagePoint.class, "meterReadings");
    }

    @Test
    public void retailCustomer_shouldNotBeMarshaled() {
        TestUtils.assertFieldNotMarshallable(UsagePoint.class, "retailCustomer");
    }

    @Test
    public void electricPowerUsageSummaries_shouldNotBeMarshaled() {
        TestUtils.assertFieldNotMarshallable(UsagePoint.class, "electricPowerUsageSummaries");
    }

    @Test
    public void localTimeParameters_shouldNotBeMarshaled() {
        TestUtils.assertFieldNotMarshallable(UsagePoint.class, "localTimeParameters");
    }
}
