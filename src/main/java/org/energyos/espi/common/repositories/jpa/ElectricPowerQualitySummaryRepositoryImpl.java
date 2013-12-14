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

import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.repositories.ElectricPowerQualitySummaryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.UUID;

@Repository
public class ElectricPowerQualitySummaryRepositoryImpl implements ElectricPowerQualitySummaryRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public ElectricPowerQualitySummary findById(Long electricPowerQualitySummaryId) {
        return em.find(ElectricPowerQualitySummary.class, electricPowerQualitySummaryId);
    }

    @Override
    @Transactional
    public void persist(ElectricPowerQualitySummary electricPowerQualitySummary) {
        em.persist(electricPowerQualitySummary);
    }

    @Override
    public ElectricPowerQualitySummary findByUUID(UUID uuid) {
        return (ElectricPowerQualitySummary) em.createNamedQuery(ElectricPowerQualitySummary.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }
}
