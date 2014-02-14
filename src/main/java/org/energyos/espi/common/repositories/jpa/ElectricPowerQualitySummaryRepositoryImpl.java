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

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.ElectricPowerQualitySummaryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

public class ElectricPowerQualitySummaryRepositoryImpl implements ElectricPowerQualitySummaryRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public ElectricPowerQualitySummary findById(Long electricPowerQualitySummaryId) {
        return em.find(ElectricPowerQualitySummary.class, electricPowerQualitySummaryId);
    }

    @Override
    @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public void persist(ElectricPowerQualitySummary electricPowerQualitySummary) {
        em.persist(electricPowerQualitySummary);
    }

    @Override
    public ElectricPowerQualitySummary findByUUID(UUID uuid) {
        return (ElectricPowerQualitySummary) em.createNamedQuery(ElectricPowerQualitySummary.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }

	@Override
	public List<Long> findAllIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void deleteById(Long id) {
		ElectricPowerQualitySummary qs = findById(id);
		UsagePoint up = qs.getUsagePoint();
		up.removeElectricPowerQualitySummary(qs);
		em.persist(em.contains(up) ? up : em.merge(up));
	    em.remove(em.contains(qs) ? qs : em.merge(qs));
	}

	@Override
        @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public void createOrReplaceByUUID(
			ElectricPowerQualitySummary electricPowerQualitySummary) {
        try {
        	ElectricPowerQualitySummary existingElectricPowerQualitySummary = findByUUID(electricPowerQualitySummary.getUUID());
        	electricPowerQualitySummary.setId(existingElectricPowerQualitySummary.getId());
            if (electricPowerQualitySummary.getUsagePoint() == null) {
            	electricPowerQualitySummary.setUsagePoint(existingElectricPowerQualitySummary.getUsagePoint());
            }
            
            if (existingElectricPowerQualitySummary.getSelfLink() != null) {
            	electricPowerQualitySummary.setSelfLink(existingElectricPowerQualitySummary.getSelfLink());
            }

            if (existingElectricPowerQualitySummary.getUpLink() != null) {
            	electricPowerQualitySummary.setUpLink(existingElectricPowerQualitySummary.getUpLink());
            }

            em.merge(electricPowerQualitySummary);
        } catch (NoResultException e) {
        	electricPowerQualitySummary.setPublished(new GregorianCalendar());
        	electricPowerQualitySummary.setUpdated(new GregorianCalendar());
            persist(electricPowerQualitySummary);
        }

		
	}
}
