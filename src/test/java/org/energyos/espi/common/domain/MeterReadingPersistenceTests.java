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

package org.energyos.espi.common.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.energyos.espi.common.support.TestUtils;
import org.hibernate.annotations.LazyCollection;
import org.junit.Test;

public class MeterReadingPersistenceTests {
	@Test
	public void persistence() {
		TestUtils.assertAnnotationPresent(MeterReading.class, Entity.class);
		TestUtils.assertAnnotationPresent(MeterReading.class, Table.class);
	}

	@Test
	public void intervalBlocks() {
		TestUtils.assertAnnotationPresent(MeterReading.class, "intervalBlocks",
				OneToMany.class);
		TestUtils.assertAnnotationPresent(MeterReading.class, "intervalBlocks",
				LazyCollection.class);
	}

	@Test
	public void usagePoint() {
		TestUtils.assertAnnotationPresent(MeterReading.class, "usagePoint",
				ManyToOne.class);
		TestUtils.assertAnnotationPresent(MeterReading.class, "usagePoint",
				JoinColumn.class);
	}

	@Test
	public void readingType() {
		TestUtils.assertAnnotationPresent(MeterReading.class, "readingType",
				ManyToOne.class);
		TestUtils.assertAnnotationPresent(MeterReading.class, "readingType",
				JoinColumn.class);
	}
}
