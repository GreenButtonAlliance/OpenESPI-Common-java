/*
 * Copyright 2013, 2014 EnergyOS.org
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

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.repositories.MeterReadingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional
public class MeterReadingRepositoryImpl implements MeterReadingRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public MeterReading findById(Long meterReadingId) {
        return em.find(MeterReading.class, meterReadingId);
    }

    @Override
    @Transactional
    public void persist(MeterReading meterReading) {
        em.persist(meterReading);
    }

    @Override
    public MeterReading findByUUID(UUID uuid) {
        return (MeterReading) em.createNamedQuery(MeterReading.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> findAllIds() {
    	List<Long> temp;
    	temp = (List<Long>)this.em.createNamedQuery(MeterReading.QUERY_FIND_ALL_IDS)
        .getResultList();
            return temp;
        }

	@Override
        @Transactional
	public void deleteById(Long id) {
	       em.remove(findById(id));
	}

	@Override
        @Transactional
	public void createOrReplaceByUUID(MeterReading meterReading) {
	        try {
	            MeterReading existingMeterReading = findByUUID(meterReading.getUUID());
	            meterReading.setId(existingMeterReading.getId());
	            if (meterReading.getUsagePoint() == null) {
	                meterReading.setUsagePoint(existingMeterReading.getUsagePoint());
	            }

	            if (existingMeterReading.getIntervalBlocks() != null) {
	                meterReading.setIntervalBlocks(existingMeterReading.getIntervalBlocks());
	            }

	            if (existingMeterReading.getReadingType() != null) {
	                meterReading.setReadingType(existingMeterReading.getReadingType());
	            }

	            if (existingMeterReading.getRelatedLinks() != null) {
	            	meterReading.setRelatedLinks(existingMeterReading.getRelatedLinks());
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
