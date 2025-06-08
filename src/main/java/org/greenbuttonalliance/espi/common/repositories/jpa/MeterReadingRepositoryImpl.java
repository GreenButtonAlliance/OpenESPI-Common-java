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

package org.greenbuttonalliance.espi.common.repositories.jpa;

import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.MeterReadingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class MeterReadingRepositoryImpl implements MeterReadingRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public MeterReading findById(Long meterReadingId) {
		return em.find(MeterReading.class, meterReadingId);
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(MeterReading meterReading) {
		em.persist(meterReading);
	}

	@Override
	public MeterReading findByUUID(UUID uuid) {
		return (MeterReading) em
				.createNamedQuery(MeterReading.QUERY_FIND_BY_UUID)
				.setParameter("uuid", uuid.toString().toUpperCase())
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
		List<Long> temp;
		temp = (List<Long>) this.em.createNamedQuery(
				MeterReading.QUERY_FIND_ALL_IDS).getResultList();
		return temp;
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void deleteById(Long id) {
		MeterReading mr = findById(id);
		UsagePoint up = mr.getUsagePoint();
		mr.removeReadingType(mr.getReadingType());
		if (up != null) {
			up.removeMeterReading(mr);
			em.persist(em.contains(up) ? up : em.merge(up));
		}
		em.remove(em.contains(mr) ? mr : em.merge(mr));
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void createOrReplaceByUUID(MeterReading meterReading) {
		try {
			MeterReading existingMeterReading = findByUUID(meterReading
					.getUUID());
			meterReading.setId(existingMeterReading.getId());
			if (meterReading.getUsagePoint() == null) {
				meterReading
						.setUsagePoint(existingMeterReading.getUsagePoint());
			}

			if (existingMeterReading.getIntervalBlocks() != null) {
				meterReading.setIntervalBlocks(existingMeterReading
						.getIntervalBlocks());
			}

			if (existingMeterReading.getReadingType() != null) {
				meterReading.setReadingType(existingMeterReading
						.getReadingType());
			}

			if (existingMeterReading.getRelatedLinks() != null) {
				meterReading.setRelatedLinks(existingMeterReading
						.getRelatedLinks());
			}

			if (existingMeterReading.getSelfLink() != null) {
				meterReading.setSelfLink(existingMeterReading.getSelfLink());
			}

			if (existingMeterReading.getUpLink() != null) {
				meterReading.setUpLink(existingMeterReading.getUpLink());
			}

			em.merge(meterReading);
		} catch (NoResultException e) {
			meterReading.setPublished(new GregorianCalendar());
			meterReading.setUpdated(new GregorianCalendar());
			persist(meterReading);
		}

	}

}
