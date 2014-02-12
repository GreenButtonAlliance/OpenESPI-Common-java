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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import javax.xml.bind.JAXBException;

import org.energyos.espi.common.utils.EspiMarshaller;
import org.junit.Test;

public class TimeConfigurationMarshallingTests {

    static final String XML_INPUT =
            "<LocalTimeParameters xmlns=\"http://naesb.org/espi\">\n" +
                    "    <dstEndRule>B40E2000</dstEndRule>\n" +
                    "    <dstOffset>3600</dstOffset>\n" +
                    "    <dstStartRule>360E2000</dstStartRule>\n" +
                    "    <tzOffset>-18000</tzOffset>\n" +
                    "</LocalTimeParameters>";

    private TimeConfiguration timeConfiguration() throws JAXBException {
        return EspiMarshaller.<TimeConfiguration>unmarshal(XML_INPUT).getValue();
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
}