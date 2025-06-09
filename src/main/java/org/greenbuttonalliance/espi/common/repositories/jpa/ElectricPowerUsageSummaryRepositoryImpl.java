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

import org.greenbuttonalliance.espi.common.domain.ElectricPowerUsageSummary;
import org.greenbuttonalliance.espi.common.domain.UsagePoint;
import org.greenbuttonalliance.espi.common.repositories.ElectricPowerUsageSummaryRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

@Repository
@Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
        jakarta.persistence.NoResultException.class,
        org.springframework.dao.EmptyResultDataAccessException.class })
public class ElectricPowerUsageSummaryRepositoryImpl implements
        ElectricPowerUsageSummaryRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public ElectricPowerUsageSummary findById(Long electricPowerUsageSummaryId) {
        return em.find(ElectricPowerUsageSummary.class,
                electricPowerUsageSummaryId);
    }

    @Override
    @Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
            jakarta.persistence.NoResultException.class,
            org.springframework.dao.EmptyResultDataAccessException.class })
    public void persist(ElectricPowerUsageSummary electricPowerUsageSummary) {
        em.persist(electricPowerUsageSummary);
    }

    @Override
    public ElectricPowerUsageSummary findByUUID(UUID uuid) {
        return (ElectricPowerUsageSummary) em
                .createNamedQuery(ElectricPowerUsageSummary.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Long> findAllIds() {
        List<Long> temp;
        temp = (List<Long>) this.em.createNamedQuery(
                ElectricPowerUsageSummary.QUERY_FIND_ALL_IDS).getResultList();
        return temp;
    }

    @Override
    @Transactional(rollbackFor = { jakarta.xml.bind.JAXBException.class }, noRollbackFor = {
            jakarta.persistence.NoResultException.class,
            org.springframework.dao.EmptyResultDataAccessException.class })
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
            ElectricPowerUsageSummary existingElectricPowerUsageSummary = findByUUID(electricPowerUsageSummary
                    .getUUID());
            electricPowerUsageSummary.setId(existingElectricPowerUsageSummary
                    .getId());
            if (electricPowerUsageSummary.getUsagePoint() == null) {
                electricPowerUsageSummary
                        .setUsagePoint(existingElectricPowerUsageSummary
                                .getUsagePoint());
            }

            if (existingElectricPowerUsageSummary.getSelfLink() != null) {
                electricPowerUsageSummary
                        .setSelfLink(existingElectricPowerUsageSummary
                                .getSelfLink());
            }

            if (existingElectricPowerUsageSummary.getUpLink() != null) {
                electricPowerUsageSummary
                        .setUpLink(existingElectricPowerUsageSummary
                                .getUpLink());
            }

            em.merge(electricPowerUsageSummary);
        } catch (NoResultException e) {
            electricPowerUsageSummary.setPublished(new GregorianCalendar());
            electricPowerUsageSummary.setUpdated(new GregorianCalendar());
            persist(electricPowerUsageSummary);
        }
    }
}
