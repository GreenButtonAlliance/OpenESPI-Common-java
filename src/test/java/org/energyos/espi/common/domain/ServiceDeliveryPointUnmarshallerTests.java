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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.bind.JAXBException;
import javax.xml.transform.stream.StreamSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/test-context.xml")
public class ServiceDeliveryPointUnmarshallerTests {
    private static String XML_INPUT = "" +
            "<espi:ServiceDeliveryPoint xmlns:espi=\"http://naesb.org/espi\">" +
            "   <espi:name>First Last</espi:name>" +
            "   <espi:tariffProfile>foo</espi:tariffProfile>" +
            "   <espi:customerAgreement>bar</espi:customerAgreement>" +
            "</espi:ServiceDeliveryPoint>";
    @Autowired
    @Qualifier("domainMarshaller")
    private Jaxb2Marshaller domainMarshaller;
    private ServiceDeliveryPoint serviceDeliveryPoint;

    @Before
    public void setup() {
        serviceDeliveryPoint = (ServiceDeliveryPoint)domainMarshaller.unmarshal(new StreamSource(new StringReader(XML_INPUT)));
    }

    @Test
    public void name() throws JAXBException, IOException {
        assertThat(serviceDeliveryPoint.getName(), is("First Last"));
    }

    @Test
    public void tariffProfile() throws JAXBException, IOException {
        assertThat(serviceDeliveryPoint.getTariffProfile(), is("foo"));
    }

    @Test
    public void customerAgreement() throws JAXBException, IOException {
        assertThat(serviceDeliveryPoint.getCustomerAgreement(), is("bar"));
    }
}
