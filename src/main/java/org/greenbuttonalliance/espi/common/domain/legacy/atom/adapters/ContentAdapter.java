/*
 *
 *     Copyright (c) 2018-2025 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.domain.legacy.atom.adapters;

import org.greenbuttonalliance.espi.common.domain.legacy.atom.ContentType;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.ObjectFactory;

import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class ContentAdapter extends
		XmlAdapter<JAXBElement<ContentType>, ContentType> {
	@Override
	public ContentType unmarshal(JAXBElement<ContentType> v) throws Exception {
		return v.getValue();
	}

	@Override
	public JAXBElement<ContentType> marshal(ContentType v) throws Exception {
		return new JAXBElement<>(ObjectFactory.EntryTypeContent_QNAME,
				ContentType.class, v);
	}
}
