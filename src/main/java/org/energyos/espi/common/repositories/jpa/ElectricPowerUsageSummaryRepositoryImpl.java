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

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.xml.bind.JAXBException;

import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.ElectricPowerUsageSummaryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (rollbackFor= {JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class ElectricPowerUsageSummaryRepositoryImpl implements ElectricPowerUsageSummaryRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public ElectricPowerUsageSummary findById(Long electricPowerUsageSummaryId) {
        return em.find(ElectricPowerUsageSummary.class, electricPowerUsageSummaryId);
    }

    @Override
    @Transactional (rollbackFor= {JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public void persist(ElectricPowerUsageSummary electricPowerUsageSummary) {
        em.persist(electricPowerUsageSummary);
    }

    @Override
    public ElectricPowerUsageSummary findByUUID(UUID uuid) {
        return (ElectricPowerUsageSummary) em.createNamedQuery(ElectricPowerUsageSummary.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Long> findAllIds() {
    	List<Long> temp;
    	temp = (List<Long>)this.em.createNamedQuery(ElectricPowerUsageSummary.QUERY_FIND_ALL_IDS)
    	        .getResultList();
            return temp;
	}

	@Override
	@Transactional (rollbackFor= {JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void deleteById(Long id) {
		ElectricPowerUsageSummary us = findById(id);
		UsagePoint up = us.getUsagePoint();
		up.removeElectricPowerUsageSummary(us);
		em.persist(em.contains(up) ? us : em.merge(up));
	    em.remove(em.contains(us) ? us : em.merge(us));
	}

	@Override
	public void createOrReplaceByUUID(
			ElectricPowerUsageSummary electricPowerUsageSummary) {

	        try {
	        	ElectricPowerUsageSummary existingElectricPowerUsageSummary = findByUUID(electricPowerUsageSummary.getUUID());
	        	electricPowerUsageSummary.setId(existingElectricPowerUsageSummary.getId());
	            if (electricPowerUsageSummary.getUsagePoint() == null) {
	            	electricPowerUsageSummary.setUsagePoint(existingElectricPowerUsageSummary.getUsagePoint());
	            }
	            
	            if (existingElectricPowerUsageSummary.getSelfLink() != null) {
	            	electricPowerUsageSummary.setSelfLink(existingElectricPowerUsageSummary.getSelfLink());
	            }

	            if (existingElectricPowerUsageSummary.getUpLink() != null) {
	            	electricPowerUsageSummary.setUpLink(existingElectricPowerUsageSummary.getUpLink());
	            }

	            em.merge(electricPowerUsageSummary);
	        } catch (NoResultException e) {
	        	electricPowerUsageSummary.setPublished(new GregorianCalendar());
	        	electricPowerUsageSummary.setUpdated(new GregorianCalendar());
	            persist(electricPowerUsageSummary);
	        }

	   }

}

