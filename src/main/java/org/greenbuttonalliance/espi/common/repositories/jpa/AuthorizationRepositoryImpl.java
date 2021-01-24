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

import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.repositories.AuthorizationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class AuthorizationRepositoryImpl implements AuthorizationRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(Authorization authorization) {
		em.persist(authorization);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIdsByApplicationInformationId(
			Long applicationInformationId) {
		return em
				.createNamedQuery(
						Authorization.QUERY_FIND_ALL_IDS_BY_APPLICATION_INFORMATION_ID)
				.setParameter("applicationInformationId",
						applicationInformationId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Authorization> findAllByRetailCustomerId(Long retailCustomerId) {
		return em
				.createNamedQuery(
						Authorization.QUERY_FIND_BY_RETAIL_CUSTOMER_ID)
				.setParameter("retailCustomerId", retailCustomerId)
				.getResultList();
	}

	@Override
	public Authorization findByState(String state) {
		return (Authorization) em
				.createNamedQuery(Authorization.QUERY_FIND_BY_STATE)
				.setParameter("state", state).getSingleResult();
	}

	@Override
	public Authorization findByScope(String scope, Long retailCustomerId) {
		return (Authorization) em
				.createNamedQuery(Authorization.QUERY_FIND_BY_SCOPE)
				.setParameter("scope", scope)
				.setParameter("retailCustomerId", retailCustomerId)
				.getSingleResult();
	}

	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void merge(Authorization authorization) {
		em.merge(authorization);
	}

	@Override
	public Authorization findById(Long authorizationId) {
		return (Authorization) em
				.createNamedQuery(Authorization.QUERY_FIND_BY_ID)
				.setParameter("id", authorizationId).getSingleResult();
	}

	@Override
	public List<Long> findAllIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Long> findAllIds(Long retailCustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Authorization findByUUID(UUID uuid) {
		return (Authorization) em
				.createNamedQuery(Authorization.QUERY_FIND_BY_UUID)
				.setParameter("uuid", uuid.toString().toUpperCase())
				.getSingleResult();
	}

	@Override
	@Transactional(rollbackFor = { javax.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void deleteById(Long id) {
		em.remove(findById(id));
	}

	@Override
	public void createOrReplaceByUUID(Authorization authorization) {
		// TODO Auto-generated method stub

	}

	@Override
	public Authorization findByAccessToken(String accessToken) {
		return (Authorization) em
				.createNamedQuery(Authorization.QUERY_FIND_BY_ACCESS_TOKEN)
				.setParameter("accessToken", accessToken).getSingleResult();
	}

	@Override
	public Authorization findByRefreshToken(String refreshToken) {
		return (Authorization) em
				.createNamedQuery(Authorization.QUERY_FIND_BY_REFRESH_TOKEN)
				.setParameter("refreshToken", refreshToken).getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIdsByBulkId(String thirdParty, Long bulkId) {
		return em.createNamedQuery(Authorization.QUERY_FIND_ALL_IDS_BY_BULK_ID)
				.setParameter("thirdParty", thirdParty)
				.setParameter("bulkId", "%BR=" + bulkId + "%").getResultList();
	}
}
