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

import org.greenbuttonalliance.espi.common.domain.ApplicationInformation;
import org.greenbuttonalliance.espi.common.repositories.ApplicationInformationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
		javax.persistence.NoResultException.class,
		org.springframework.dao.EmptyResultDataAccessException.class })
public class ApplicationInformationRepositoryImpl implements
		ApplicationInformationRepository {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public ApplicationInformation findById(Long id) {
		Assert.notNull(id, "id is required");
		return (ApplicationInformation) em
				.createNamedQuery(ApplicationInformation.QUERY_FIND_BY_ID)
				.setParameter("id", id).getSingleResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ApplicationInformation> findByKind(String kind) {
		return (List<ApplicationInformation>) this.em
				.createNamedQuery(ApplicationInformation.QUERY_FIND_BY_KIND)
				.setParameter("kind", kind).getResultList();

	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ApplicationInformation> findAll() {
		return (List<ApplicationInformation>) this.em.createNamedQuery(
				ApplicationInformation.QUERY_FIND_ALL).getResultList();
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void persist(ApplicationInformation applicationInformation) {
		em.persist(applicationInformation);
	}

	@Override
	public ApplicationInformation findByUUID(UUID uuid) {
		return (ApplicationInformation) em
				.createNamedQuery(ApplicationInformation.QUERY_FIND_BY_UUID)
				.setParameter("uuid", uuid.toString().toUpperCase())
				.getSingleResult();
	}

	@Override
	public ApplicationInformation findByClientId(String clientId) {
		Assert.notNull(clientId, "clientId is required");
		return (ApplicationInformation) em
				.createNamedQuery(
						ApplicationInformation.QUERY_FIND_BY_CLIENT_ID)
				.setParameter("clientId", clientId).getSingleResult();
	}

	@Override
	public ApplicationInformation findByDataCustodianClientId(
			String dataCustodianId) {
		return (ApplicationInformation) em
				.createNamedQuery(
						ApplicationInformation.QUERY_FIND_BY_DATA_CUSTODIAN_CLIENT_ID)
				.setParameter("dataCustodianId", dataCustodianId)
				.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
		return (List<Long>) this.em.createNamedQuery(
				ApplicationInformation.QUERY_FIND_ALL_IDS).getResultList();
	}

	@Override
	@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
			javax.persistence.NoResultException.class,
			org.springframework.dao.EmptyResultDataAccessException.class })
	public void deleteById(Long id) {
		em.remove(findById(id));

	}
}
