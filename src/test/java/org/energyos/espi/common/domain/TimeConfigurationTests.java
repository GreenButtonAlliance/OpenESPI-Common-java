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

import com.sun.syndication.io.FeedException;
import org.custommonkey.xmlunit.exceptions.XpathException;
import org.energyos.espi.common.atom.XMLTest;
import org.energyos.espi.common.utils.XMLMarshaller;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import javax.persistence.Entity;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static org.energyos.espi.common.support.TestUtils.assertAnnotationPresent;
import static org.energyos.espi.common.test.Asserts.assertXpathValue;
import static org.energyos.espi.common.test.EspiFactory.newTimeConfigurationWithUsagePoint;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class TimeConfigurationTests extends XMLTest {

    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;

    static final String XML_INPUT =
            "<LocalTimeParameters xmlns=\"http://naesb.org/espi\">\n" +
                    "    <dstEndRule>B40E2000</dstEndRule>\n" +
                    "    <dstOffset>3600</dstOffset>\n" +
                    "    <dstStartRule>360E2000</dstStartRule>\n" +
                    "    <tzOffset>-18000</tzOffset>\n" +
                    "</LocalTimeParameters>";

    private TimeConfiguration timeConfiguration() throws JAXBException {
        XMLMarshaller xmlMarshaller = new XMLMarshaller();
        xmlMarshaller.setMarshaller(marshaller);

        return xmlMarshaller.unmarshal(XML_INPUT, TimeConfiguration.class);
    }

    @Test
    public void persistable() {
        assertAnnotationPresent(TimeConfiguration.class, Entity.class);
    }

    @Test
    public void unmarshal_timeConfiguration() throws JAXBException {
        assertEquals(TimeConfiguration.class, timeConfiguration().getClass());
    }

    @Test
    public void unmarshal_setsDstEndRule() throws UnsupportedEncodingException, JAXBException {
        assertTrue(Arrays.equals(timeConfiguration().getDstEndRule(), new byte[]{-76, 14, 32, 0}));
    }

    @Test
    public void unmarshal_setDstOffset() throws JAXBException {
        assertEquals(3600L, timeConfiguration().getDstOffset());
    }

    @Test
    public void unmarshal_setDstStartRule() throws JAXBException {
        assertTrue(Arrays.equals(timeConfiguration().getDstStartRule(), new byte[]{54, 14, 32, 0}));
    }

    @Test
    public void unmarshal_setTzOffset() throws JAXBException {
        assertEquals(-18000L, timeConfiguration().getTzOffset());
    }

    @Test
    public void marshal_setsDstEndRule() throws FeedException, SAXException, IOException, XpathException {
        assertXpathValue("666F6F", "/espi:LocalTimeParameters/espi:dstEndRule", xmlResult());
    }

    @Test
    public void marshal_setsDstOffset() throws FeedException, SAXException, IOException, XpathException {
        assertXpathValue("1000", "/espi:LocalTimeParameters/espi:dstOffset", xmlResult());
    }

    @Test
    public void marshal_setsDstStartRule() throws FeedException, SAXException, IOException, XpathException {
        assertXpathValue("626172", "/espi:LocalTimeParameters/espi:dstStartRule", xmlResult());
    }

    @Test
    public void marshal_setsTzOffset() throws FeedException, SAXException, IOException, XpathException {
       assertXpathValue("1234", "/espi:LocalTimeParameters/espi:tzOffset", xmlResult());
    }

    private String xmlResult() throws FeedException {
        XMLMarshaller xmlMarshaller = new XMLMarshaller();
        xmlMarshaller.setMarshaller(marshaller);

        return xmlMarshaller.marshal(newTimeConfigurationWithUsagePoint());
    }
}