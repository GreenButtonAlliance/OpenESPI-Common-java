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

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.TimeConfigurationRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.UUID;

@Repository
public class TimeConfigurationRepositoryImpl implements TimeConfigurationRepository {

    @PersistenceContext
    protected EntityManager em;

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}
	
    @Override
    public TimeConfiguration findById(Long timeConfigurationId) {
        return em.find(TimeConfiguration.class, timeConfigurationId);
    }
  
    @Override
    public TimeConfiguration findByUUID(UUID uuid) {
        return (TimeConfiguration) em.createNamedQuery(TimeConfiguration.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }
	
    @Override
    @Transactional
    public void persist(TimeConfiguration timeConfiguration) {
        em.persist(timeConfiguration);
    }

}
