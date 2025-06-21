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

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.greenbuttonalliance.espi.common.domain.legacy.ElectricPowerQualitySummary;
import org.greenbuttonalliance.espi.common.domain.legacy.IdentifiedObject;
import org.greenbuttonalliance.espi.common.domain.legacy.IntervalBlock;
import org.greenbuttonalliance.espi.common.domain.legacy.MeterReading;
import org.greenbuttonalliance.espi.common.domain.legacy.ReadingType;
import org.greenbuttonalliance.espi.common.domain.legacy.TimeConfiguration;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.service.ResourceService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntryTypeIterator {

	private EntryBuilder builder;

	private Iterator<Long> resourceIds;
	@SuppressWarnings("rawtypes")
	private Iterator<Pair<Long, Class>> childIds = new ArrayList<Pair<Long, Class>>().iterator();
	private ResourceService resourceService;

	@SuppressWarnings("rawtypes")
	// TODO: fix the EntryTypeIterator Typing System
	private Class rootClass;

	private Long subscriptionId;

	public EntryTypeIterator(ResourceService resourceService, List<Long> ids,
			EntryBuilder builder) {
		this.resourceService = resourceService;
		this.resourceIds = ids.iterator();
		this.builder = builder;
	}

	@SuppressWarnings("rawtypes")
	// TODO: fix the EntryTypeIterator Typing System
	public EntryTypeIterator(ResourceService resourceService, List<Long> ids,
			Class clazz) {
		this.resourceService = resourceService;
		this.resourceIds = ids.iterator();
		builder = new EntryBuilder();
		rootClass = clazz;
	}

	public boolean hasNext() {
		return childIds.hasNext() || resourceIds.hasNext();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	// TODO: fix the EntryTypeIterator Typing System
	public EntryType next() {
		IdentifiedObject resource;

		if (childIds.hasNext()) {
			Pair<Long, Class> pair = childIds.next();
			resource = resourceService.findById(pair.getKey(), pair.getValue());
		} else {
			resource = resourceService.findById(resourceIds.next(), rootClass);
			updateChildIds(resource.getId());
		}

		return builder.build(resource);
	}

	// For the RESTful interfaces, we don't want to build the whole child
	// structure,
	// only the 1 resource is exported.
	//
	@SuppressWarnings({ "unchecked", "rawtypes" })
	// TODO: fix the EntryTypeIterator Typing System
	public EntryType nextEntry(Class resourceClass) {
		IdentifiedObject resource;
		resource = resourceService.findById(resourceIds.next(), resourceClass);
		return builder.buildEntry(resource);
	}

	@SuppressWarnings("rawtypes")
	// TODO: fix the EntryTypeIterator Typing System
	private void updateChildIds(Long usagePointId) {
		// TODO: Deal with these Class warnings...

		List<Pair<Long, Class>> pairs = new ArrayList<>();
		for (Long id : resourceService.findAllIdsByUsagePointId(usagePointId,
				TimeConfiguration.class)) {
			pairs.add(new ImmutablePair<>(id,
					TimeConfiguration.class));
		}
		for (Long id : resourceService.findAllIdsByUsagePointId(usagePointId,
				MeterReading.class)) {
			pairs.add(new ImmutablePair<>(id, MeterReading.class));
		}
		for (Long id : resourceService.findAllIdsByUsagePointId(usagePointId,
				IntervalBlock.class)) {
			pairs.add(new ImmutablePair<>(id, IntervalBlock.class));
		}
		// ElectricPowerUsageSummary removed - deprecated resource
		// for (Long id : resourceService.findAllIdsByUsagePointId(usagePointId,
		//		ElectricPowerUsageSummary.class)) {
		//	pairs.add(new ImmutablePair<>(id,
		//			ElectricPowerUsageSummary.class));
		// }
		for (Long id : resourceService.findAllIdsByUsagePointId(usagePointId,
				ElectricPowerQualitySummary.class)) {
			pairs.add(new ImmutablePair<>(id,
					ElectricPowerQualitySummary.class));
		}
		for (Long id : resourceService.findAllIdsByUsagePointId(usagePointId,
				ReadingType.class)) {
			pairs.add(new ImmutablePair<>(id, ReadingType.class));
		}
		childIds = pairs.iterator();
	}

	public void setResourceService(ResourceService resourceService) {
		this.resourceService = resourceService;
	}

	public void setSubscriptionId(Long subscriptionId) {
		this.subscriptionId = subscriptionId;
	}

	public Long getSubscriptionId() {
		return this.subscriptionId;
	}
}
