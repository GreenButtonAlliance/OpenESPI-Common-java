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

package org.greenbuttonalliance.espi.common.service.impl;

import org.greenbuttonalliance.espi.common.domain.legacy.*;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.EntryType;
import org.greenbuttonalliance.espi.common.repositories.usage.ResourceRepository;
import org.greenbuttonalliance.espi.common.service.ResourceService;
import org.greenbuttonalliance.espi.common.utils.EntryTypeIterator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ResourceServiceImpl implements ResourceService {

	private final ResourceRepository repository;

	public ResourceServiceImpl(ResourceRepository repository) {
		this.repository = repository;
	}

	/**
	 * A private Map of Key:Value strings to hold the dynamic configuration.
	 * 
	 * SFTPCacheRoot: "./pendingdelivery/" AutomaticNotificationPropagation:
	 * [true | false]
	 * 
	 */
	private Map<String, String> params;

	@Override
	public void persist(IdentifiedObject resource) {
		repository.persist(resource);
	}

	@Override
	public List<IdentifiedObject> findByAllParentsHref(String relatedHref,
			Linkable linkable) {
		try {

			if (linkable instanceof UsagePoint) {
				return new ArrayList<>();
			} else {
				return repository.findAllParentsByRelatedHref(relatedHref,
						linkable);
			}
		} catch (Exception e) {

			return new ArrayList<>();
		}
	}

	@Override
	public List<IdentifiedObject> findAllRelated(Linkable linkable) {
		List<IdentifiedObject> temp = repository.findAllRelated(linkable);
		return temp;
	}

	/**
	 * Returns an list of the resources contained in the primary children
	 * collection of the a parent resource. The resources are returned lazily
	 * within a transactional context allowing for persistent mutation.
	 *
	 * @param Long
	 *            parentResourceID used to retrieve the Resource
	 * @param Class
	 *            the class of the parent resource
	 * @return List<Resources> of class appropriate to the children of the
	 *         parent resource
	 * @see IdentifiedObject and Resource
	 */
	public <T extends IdentifiedObject> List<IdentifiedObject> findAllContainedChildren(
			Long id, Class<T> parentClass) {
		List<IdentifiedObject> result = new ArrayList<IdentifiedObject>();

		if (UsagePoint.class.equals(parentClass)) {
			// the children are the MeterReadings
			UsagePoint p = findById(id, UsagePoint.class);
			for (MeterReading o : p.getMeterReadings()) {
				result.add((IdentifiedObject) o);
			}
		}

		if (MeterReading.class.equals(parentClass)) {
			// the children are the MeterReadings
			MeterReading p = findById(id, MeterReading.class);
			for (IntervalBlock o : p.getIntervalBlocks()) {
				result.add((IdentifiedObject) o);
			}
		}

		if (ReadingType.class.equals(parentClass)) {
			// ReadingType doesn't have any
		}

		if (IntervalBlock.class.equals(parentClass)) {
			// IntervalBlock doesn't have any IdentifiedObject children
			// but we may want to return the IntervalReadings
		}

		if (ElectricPowerQualitySummary.class.equals(parentClass)) {
			// ElectricPowerQualitySummary doesn't have any
		}

		// ElectricPowerUsageSummary removed - deprecated resource
		// if (ElectricPowerUsageSummary.class.equals(parentClass)) {
		//	// ElectricPowerUsageSummary doesn't have any
		// }

		if (TimeConfiguration.class.equals(parentClass)) {
			// the children are the UsagePoints
			// TODO: find all usage points in this local time
		}

		if (Subscription.class.equals(parentClass)) {
			// the children are the Subscription
			Subscription p = findById(id, Subscription.class);
			for (UsagePoint o : p.getUsagePoints()) {
				result.add((IdentifiedObject) o);
			}
		}

		if (Authorization.class.equals(parentClass)) {
			// Authorizations have no children (yet)
		}

		if (MeterReading.class.equals(parentClass)) {

		}

		return result;
	}

	@Override
	public <T> T findByUUID(UUID uuid, Class<T> clazz) {
		return repository.findByUUID(uuid, clazz);
	}

	@Override
	@Transactional(readOnly = true)
	public <T extends IdentifiedObject> T testById(Long id, Class<T> clazz) {
		return repository.findById(id, clazz);
	}

	@Override
	public <T extends IdentifiedObject> T findById(Long id, Class<T> clazz) {
		return repository.findById(id, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz) {
		return repository.findAllIds(clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(
			Long id, Class<T> clazz) {
		return repository.findAllIdsByUsagePointId(id, clazz);
	}

	// XPath Accessors
	//

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(
			Class<T> clazz) {
		return repository.findAllIdsByXPath(clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, id2, clazz);
	}

	@Override
	public <T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Long id3, Class<T> clazz) {
		return repository.findAllIdsByXPath(id1, id2, id3, clazz);
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1,
			Class<T> clazz) {
		return repository.findIdByXPath(id1, clazz);
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, clazz);
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, id3, clazz);
	}

	@Override
	public <T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz) {
		return repository.findIdByXPath(id1, id2, id3, id4, clazz);
	}


	@Override
	public <T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(
			Class<T> clazz) {
		List<Long> idList = repository.findAllIds(clazz);
		return findEntryTypeIterator(idList, clazz);
	}

	@Override
	public <T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(
			List<Long> ids, Class<T> clazz) {
		List<Long> idList = ids;
		EntryTypeIterator result = null;
		try {
			result = (new EntryTypeIterator(this, idList, clazz));
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	@Override
	public <T extends IdentifiedObject> EntryType findEntryType(long id1,
			Class<T> clazz) {
		EntryType result = null;
		try {
			List<Long> temp = new ArrayList<Long>();
			temp.add(id1);
			result = (new EntryTypeIterator(this, temp, clazz))
					.nextEntry(clazz);
		} catch (Exception e) {
			result = null;
		}
		return result;
	}

	@Override
	public <T extends IdentifiedObject> T findByResourceUri(String uri,
			Class<T> clazz) {
		return repository.findByResourceUri(uri, clazz);
	}

	@Override
	public void flush() {

		repository.flush();
	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteById(Long id, Class<T> clazz) {

		repository.deleteById(id, clazz);
	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Class<T> clazz) {
		repository.deleteByXPathId(id1, id2, clazz);

	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Long id3, Class<T> clazz) {
		repository.deleteByXPathId(id1, id2, id3, clazz);

	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> void deleteByXPathId(Long id1,
			Long id2, Long id3, Long id4, Class<T> clazz) {
		repository.deleteByXPathId(id1, id2, id3, id4, clazz);

	}

	@Transactional
	@Override
	public <T extends IdentifiedObject> T merge(IdentifiedObject resource) {
		return repository.merge(resource);

	}


	@Override
	public void setConfigurations(Map<String, String> params) {
		this.params = params;
	}

	@Override
	public String getConfiguration(String key) {
		return this.params.get(key);
	}

	@Override
	public void setConfiguration(String key, String value) {
		this.params.put(key, value);
	}

	@Override
	public Map<String, String> getConfigurations() {
		return this.params;
	}

}
