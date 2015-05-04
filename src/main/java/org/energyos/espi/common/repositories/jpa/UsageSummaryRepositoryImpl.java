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

import org.energyos.espi.common.domain.UsageSummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.UsageSummaryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class UsageSummaryRepositoryImpl implements
		UsageSummaryRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public UsageSummary findById(Long UsageSummaryId) {
		return em.find(UsageSummary.class,
				UsageSummaryId);
	}

	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(UsageSummary UsageSummary) {
		em.persist(UsageSummary);
	}

	@Override
	public UsageSummary findByUUID(UUID uuid) {
		return (UsageSummary) em
				.createNamedQuery(UsageSummary.QUERY_FIND_BY_UUID)
				.setParameter("uuid", uuid.toString().toUpperCase())
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
		List<Long> temp;
		temp = (List<Long>) this.em.createNamedQuery(
				UsageSummary.QUERY_FIND_ALL_IDS).getResultList();
		return temp;
	}

	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void deleteById(Long id) {
		UsageSummary us = findById(id);
		UsagePoint up = us.getUsagePoint();
		up.removeUsageSummary(us);
		em.persist(em.contains(up) ? us : em.merge(up));
		em.remove(em.contains(us) ? us : em.merge(us));
	}

	@Override
	public void createOrReplaceByUUID(
			UsageSummary UsageSummary) {

		try {
			UsageSummary existingUsageSummary = findByUUID(UsageSummary
					.getUUID());
			UsageSummary.setId(existingUsageSummary
					.getId());
			if (UsageSummary.getUsagePoint() == null) {
				UsageSummary
						.setUsagePoint(existingUsageSummary
								.getUsagePoint());
			}

			if (existingUsageSummary.getSelfLink() != null) {
				UsageSummary
						.setSelfLink(existingUsageSummary
								.getSelfLink());
			}

			if (existingUsageSummary.getUpLink() != null) {
				UsageSummary
						.setUpLink(existingUsageSummary
								.getUpLink());
			}

			em.merge(UsageSummary);
		} catch (NoResultException e) {
			UsageSummary.setPublished(new GregorianCalendar());
			UsageSummary.setUpdated(new GregorianCalendar());
			persist(UsageSummary);
		}

	}

}
