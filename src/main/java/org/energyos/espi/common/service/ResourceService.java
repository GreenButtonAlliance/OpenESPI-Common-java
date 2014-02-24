/*
 * Copyright 2013, 2014 EnergyOS.org
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

package org.energyos.espi.common.service;

import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.utils.EntryTypeIterator;

public interface ResourceService {
	void persist(IdentifiedObject resource);
	
	void flush();

	List<IdentifiedObject> findByAllParentsHref(String relatedHref,
			Linkable linkable);

	List<IdentifiedObject> findAllRelated(Linkable resource);

	<T> T findByUUID(UUID uuid, Class<T> clazz);

	<T extends IdentifiedObject> T testById(Long id, Class<T> clazz);
	
	<T extends IdentifiedObject> T findById(Long id, Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(Long id, Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Long id2, Class<T> clazz);
	
	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1, Long id2, Long id3, Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Class<T> clazz);
	
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2, Class<T> clazz);
	
	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2, Long id3, Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2, Long id3, Long id4, Class<T> clazz);

	<T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(Class<T> clazz);
	
	<T extends IdentifiedObject> EntryTypeIterator findEntryTypeIterator(List<Long> ids, Class<T> clazz);

	<T extends IdentifiedObject> EntryType findEntryType(long id1, Class<T> clazz);
	
	// currently used only finding the Authorization associated with a given resource URI
	<T extends IdentifiedObject> T findByResourceUri(String uri, Class<T> clazz);

	// a collection of interfaces that delete resources (by both XPATH and ROOT form)
	//
	<T extends IdentifiedObject> void deleteById(Long id, Class<T> clazz);

	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2,Class<T> clazz);
	
	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2, Long id3, Class<T> clazz);
	
	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2, Long id3, Long id4, Class<T> clazz);

	<T extends IdentifiedObject> T merge(IdentifiedObject existingResource);
	
}
