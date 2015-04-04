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

package org.energyos.espi.common.repositories;

import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.Linkable;
import org.energyos.espi.common.domain.UsagePoint;
import org.springframework.transaction.annotation.Transactional;

public interface ResourceRepository {
	void persist(IdentifiedObject resource);

	void flush();

	List<IdentifiedObject> findAllParentsByRelatedHref(String href,
			Linkable linkable);

	List<IdentifiedObject> findAllRelated(Linkable linkable);

	<T> T findByUUID(UUID uuid, Class<T> clazz);

	UsagePoint findByUUID(UUID uuid);

	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	void update(UsagePoint resource);

	<T extends IdentifiedObject> T findById(Long id, Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIds(Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIdsByUsagePointId(
			Long usagePointId, Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Long id1,
			Long id2, Long id3, Class<T> clazz);

	<T extends IdentifiedObject> List<Long> findAllIdsByXPath(Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Class<T> clazz);

	<T extends IdentifiedObject> Long findIdByXPath(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz);

	<T extends IdentifiedObject> T findByResourceUri(String uri, Class<T> clazz);

	@Transactional
	<T extends IdentifiedObject> void deleteById(Long id, Class<T> clazz);

	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2,
			Class<T> clazz);

	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2,
			Long id3, Class<T> clazz);

	<T extends IdentifiedObject> void deleteByXPathId(Long id1, Long id2,
			Long id3, Long id4, Class<T> clazz);

	<T extends IdentifiedObject> T merge(IdentifiedObject resource);

}
