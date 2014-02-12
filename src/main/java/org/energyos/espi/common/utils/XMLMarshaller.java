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

package org.energyos.espi.common.utils;

import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBElement;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;

import com.sun.syndication.io.FeedException;

@Component
public class XMLMarshaller {

    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;

    public <T extends Object> T unmarshal(InputStream stream, Class<T> type) {
        return unmarshal(new StreamSource(stream), type);
    }

    public <T extends Object> T unmarshal(String string, Class<T> type) {
        StringReader reader = new StringReader(string);
        return unmarshal(new StreamSource(reader), type);
    }

    public String marshal(Object entity) throws FeedException {
        StringWriter sw = new StringWriter();
        marshaller.marshal(entity, new StreamResult(sw));

        return sw.toString();
    }

    public void setMarshaller(Jaxb2Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    private <T extends Object> T unmarshal(StreamSource streamSource, Class<T> type) {
        JAXBElement jaxbElement = (JAXBElement)marshaller.unmarshal(streamSource);
        return type.cast(jaxbElement.getValue());
    }


}