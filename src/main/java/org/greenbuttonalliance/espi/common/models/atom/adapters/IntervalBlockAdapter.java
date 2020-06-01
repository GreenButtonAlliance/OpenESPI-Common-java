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

package org.greenbuttonalliance.espi.common.models.atom.adapters;

import org.greenbuttonalliance.espi.common.domain.IntervalBlock;
import org.greenbuttonalliance.espi.common.domain.IntervalReading;
import org.greenbuttonalliance.espi.common.domain.ObjectFactory;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntervalBlockAdapter extends
		XmlAdapter<JAXBElement<IntervalBlock>, IntervalBlock> {

	@Override
	public IntervalBlock unmarshal(JAXBElement<IntervalBlock> v)
			throws Exception {
		IntervalBlock intervalBlock = v.getValue();

		for (IntervalReading intervalReading : intervalBlock
				.getIntervalReadings()) {
			intervalReading.setIntervalBlock(intervalBlock);
		}

		return intervalBlock;
	}

	@Override
	public JAXBElement<IntervalBlock> marshal(IntervalBlock v) throws Exception {
		if (v == null) {
			return null;
		}
		return new JAXBElement<>(ObjectFactory.IntervalBlock_QNAME,
				IntervalBlock.class, v);
	}
}
