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

import org.greenbuttonalliance.espi.common.domain.IntervalBlock;
import org.greenbuttonalliance.espi.common.domain.MeterReading;
import org.greenbuttonalliance.espi.common.repositories.IntervalBlockRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		jakarta.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class IntervalBlockRepositoryImpl implements IntervalBlockRepository {

	@PersistenceContext
	protected EntityManager em;

	@SuppressWarnings("unchecked")
	@Override
	public List<IntervalBlock> findAllByMeterReadingId(Long meterReadingId) {
		return (List<IntervalBlock>) this.em
				.createNamedQuery(IntervalBlock.QUERY_ALL_BY_METER_READING_ID)
				.setParameter("meterReadingId", meterReadingId).getResultList();
	}

	@Override
	public IntervalBlock findById(Long intervalBlockId) {
		return em.find(IntervalBlock.class, intervalBlockId);
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			jakarta.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(IntervalBlock intervalBlock) {
		em.persist(intervalBlock);

	}

	@Override
	public IntervalBlock findByUUID(UUID uuid) {
		return (IntervalBlock) em
				.createNamedQuery(IntervalBlock.QUERY_FIND_BY_UUID)
				.setParameter("uuid", uuid.toString().toUpperCase())
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
		List<Long> temp;
		temp = (List<Long>) this.em.createNamedQuery(
				IntervalBlock.QUERY_FIND_ALL_IDS).getResultList();
		return temp;
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			jakarta.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void deleteById(Long id) {
		IntervalBlock ib = findById(id);
		MeterReading mr = ib.getMeterReading();
		if (mr != null) {
			mr.removeIntervalBlock(ib);
			em.persist(em.contains(mr) ? mr : em.merge(mr));
		}
		em.remove(em.contains(ib) ? ib : em.merge(ib));
	}

	@Override
	public void createOrReplaceByUUID(IntervalBlock intervalBlock) {
		// TODO Auto-generated method stub

	}
}
