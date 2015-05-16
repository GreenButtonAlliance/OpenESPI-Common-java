/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
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

package org.energyos.espi.common.models.atom.adapters;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.energyos.espi.common.domain.GrantType;
import org.energyos.espi.common.domain.ObjectFactory;

public class GrantTypeAdapter extends
		XmlAdapter<JAXBElement<GrantType>, GrantType> {

	@Override
	public GrantType unmarshal(JAXBElement<GrantType> v) throws Exception {
		GrantType grantType = v.getValue();
		return grantType;
	}

	@Override
	public JAXBElement<GrantType> marshal(GrantType v) throws Exception {
		if (v == null) {
			return null;
		}
		return new JAXBElement<>(ObjectFactory.GrantType_QNAME,
				GrantType.class, v);

	}
}