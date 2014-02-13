/*
 * Copyright 2013 EnergyOS.org
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

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.repositories.ApplicationInformationRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class ApplicationInformationRepositoryImpl implements ApplicationInformationRepository {

    @PersistenceContext
    protected EntityManager em;


    @Override
    public ApplicationInformation findById(Long id) {
        return (ApplicationInformation)em.createNamedQuery(ApplicationInformation.QUERY_FIND_BY_ID)
                .setParameter("id", id).getSingleResult();
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ApplicationInformation> findAll() {
        return (List<ApplicationInformation>)this.em
                .createNamedQuery(ApplicationInformation.QUERY_FIND_ALL).getResultList();
    }

    @Override
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public void persist(ApplicationInformation applicationInformation) {
        em.persist(applicationInformation);
    }

    @Override
    public ApplicationInformation findByUUID(UUID uuid) {
        return (ApplicationInformation) em.createNamedQuery(ApplicationInformation.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }

    @Override
    public ApplicationInformation findByClientId(String clientId) {
        return (ApplicationInformation)em.createNamedQuery(ApplicationInformation.QUERY_FIND_BY_CLIENT_ID)
                .setParameter("clientId", clientId).getSingleResult();
    }

    @Override
    public ApplicationInformation findByDataCustodianClientId(String dataCustodianId) {
        return (ApplicationInformation)em.createNamedQuery(ApplicationInformation.QUERY_FIND_BY_DATA_CUSTODIAN_CLIENT_ID)
                .setParameter("dataCustodianId", dataCustodianId).getSingleResult();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
        return (List<Long>)this.em.createNamedQuery(ApplicationInformation.QUERY_FIND_ALL_IDS)
                .getResultList();
	}

	@Override
	@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void deleteById(Long id) {
	       em.remove(findById(id));
		
	}
}
