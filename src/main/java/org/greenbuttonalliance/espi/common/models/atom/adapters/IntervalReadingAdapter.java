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

package org.greenbuttonalliance.espi.common.models.atom.adapters;

import org.greenbuttonalliance.espi.common.domain.IntervalReading;
import org.greenbuttonalliance.espi.common.domain.ObjectFactory;
import org.greenbuttonalliance.espi.common.domain.ReadingQuality;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

public class IntervalReadingAdapter extends
		XmlAdapter<JAXBElement<IntervalReading>, IntervalReading> {

	@Override
	public IntervalReading unmarshal(JAXBElement<IntervalReading> v)
			throws Exception {
		IntervalReading intervalReading = v.getValue();

		for (ReadingQuality readingQuality : intervalReading
				.getReadingQualities()) {
			readingQuality.setIntervalReading(intervalReading);
		}

		return v.getValue();
	}

	@Override
	public JAXBElement<IntervalReading> marshal(IntervalReading v)
			throws Exception {
		return new ObjectFactory().createIntervalReading(v);
	}
}
