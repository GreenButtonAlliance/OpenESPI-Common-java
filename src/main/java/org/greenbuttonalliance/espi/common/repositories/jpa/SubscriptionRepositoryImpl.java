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

import org.greenbuttonalliance.espi.common.domain.Subscription;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.SubscriptionRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class SubscriptionRepositoryImpl implements SubscriptionRepository {

	@PersistenceContext
	protected EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(Subscription subscription) {
		if (subscription.getHashedId() == null)
			subscription.setHashedId(UUID.randomUUID().toString());
		em.persist(subscription);
	}

	@Override
	public void merge(Subscription subscription) {
		em.merge(subscription);
	}

	@Override
	public Subscription findByUUID(UUID uuid) {
		return (Subscription) em
				.createNamedQuery(Subscription.QUERY_FIND_BY_UUID)
				.setParameter("uuid", uuid.toString().toUpperCase())
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Subscription> findAll() {
		return (List<Subscription>) this.em.createNamedQuery(
				Subscription.QUERY_FIND_ALL).getResultList();
	}

	@Override
	public Subscription findByHashedId(String hashedId) {
		return (Subscription) em
				.createNamedQuery(Subscription.QUERY_FIND_BY_HASHED_ID)
				.setParameter("hashedId", hashedId).getSingleResult();
	}

	@Override
	public Subscription findById(Long id) {
		return (Subscription) em
				.createNamedQuery(Subscription.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void deleteById(Long id) {
		Subscription subscription = findById(id);
		List<UsagePoint> ups = subscription.getUsagePoints();

		for (UsagePoint up : ups) {
			up.removeSubscription(subscription);
			em.persist(em.contains(up) ? up : em.merge(up));
		}
		em.remove(em.contains(subscription) ? subscription : em
				.merge(subscription));
	}

	@Override
	public Subscription findByAuthorizationId(Long id) {
		return (Subscription) em
				.createNamedQuery(Subscription.QUERY_FIND_BY_AUTHORIZATION_ID)
				.setParameter("id", id).getSingleResult();
	}

}
