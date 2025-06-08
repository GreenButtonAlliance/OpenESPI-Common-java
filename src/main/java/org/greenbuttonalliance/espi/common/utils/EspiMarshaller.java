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

package org.greenbuttonalliance.espi.common.utils;

import com.sun.syndication.io.FeedException;
import org.springframework.util.Assert;

import jakarta.xml.bind.*;
import java.io.ByteArrayInputStream;
import java.io.StringWriter;

public class EspiMarshaller {

	private EspiMarshaller() {
	}

	private static JAXBContext jaxbContext;
	private static Marshaller marshaller;
	private static Unmarshaller unmarshaller;

	@SuppressWarnings("unchecked")
	// TODO: make RetailCustomer Inherit from Identified Object to fix @Suppress
	public static <T> JAXBElement<T> unmarshal(String xml) throws JAXBException {
		Assert.notNull(xml, "XML string must not be null!");
		return (JAXBElement<T>) getUnmarshaller().unmarshal(new ByteArrayInputStream(xml.getBytes()));
	}

	public static String marshal(Object entity) throws FeedException {
		Assert.notNull(entity, "Entity must not be null");
		StringWriter sw = new StringWriter();

		try {
			getMarshaller().marshal(entity, sw);
		} catch (JAXBException e) {
			throw new FeedException("Invalid " + entity.getClass().toString()
					+ ". Could not serialize.");
		}
		return sw.toString();
	}

	private static Marshaller getMarshaller() throws JAXBException {
		if (marshaller == null) {
			JAXBContext jaxbContext = getJaxbContext();
			marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
					Boolean.TRUE);
		}

		return marshaller;
	}

	private static Unmarshaller getUnmarshaller() throws JAXBException {
		if (unmarshaller == null) {
			JAXBContext jaxbContext = getJaxbContext();
			unmarshaller = jaxbContext.createUnmarshaller();
		}

		return unmarshaller;
	}

	private static JAXBContext getJaxbContext() throws JAXBException {
		if (jaxbContext == null) {
			jaxbContext = JAXBContext
					.newInstance("org.greenbuttonalliance.espi.common.domain");
		}

		return jaxbContext;
	}
}
