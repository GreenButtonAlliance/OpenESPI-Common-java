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

package org.energyos.espi.common.repositories.jpa;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.repositories.ReadingTypeRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class ReadingTypeRepositoryImpl implements ReadingTypeRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public ReadingType findById(Long readingTypeId) {
		return em.find(ReadingType.class, readingTypeId);
	}

	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(ReadingType readingType) {
		em.persist(readingType);
	}

	@Override
	public ReadingType findByUUID(UUID uuid) {
		return (ReadingType) em
				.createNamedQuery(ReadingType.QUERY_FIND_BY_UUID)
				.setParameter("uuid", uuid.toString().toUpperCase())
				.getSingleResult();
	}

	@Override
	public void createOrReplaceByUUID(ReadingType readingType) {
		try {
			ReadingType existingReadingType = findByUUID(readingType.getUUID());
			readingType.setId(existingReadingType.getId());

			if (existingReadingType.getSelfLink() != null) {
				readingType.setSelfLink(existingReadingType.getSelfLink());
			}

			if (existingReadingType.getUpLink() != null) {
				readingType.setUpLink(existingReadingType.getUpLink());
			}

			em.merge(readingType);
		} catch (NoResultException e) {
			readingType.setPublished(new GregorianCalendar());
			readingType.setUpdated(new GregorianCalendar());
			persist(readingType);
		}

	}

	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void deleteById(Long id) {
		ReadingType rt = findById(id);
		// TODO may need to find any/all MeterReadings that point to this ...
		em.remove(em.contains(rt) ? rt : em.merge(rt));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
		List<Long> temp;
		temp = (List<Long>) this.em.createNamedQuery(
				ReadingType.QUERY_FIND_ALL_IDS).getResultList();
		return temp;
	}
}
