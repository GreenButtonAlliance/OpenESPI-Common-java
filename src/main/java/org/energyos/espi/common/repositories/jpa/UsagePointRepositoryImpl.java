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

import org.energyos.espi.common.domain.*;
import org.energyos.espi.common.repositories.UsagePointRepository;
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
public class UsagePointRepositoryImpl implements UsagePointRepository {

    @PersistenceContext
    protected EntityManager em;

    @SuppressWarnings("unchecked")
    public List<UsagePoint> findAllByRetailCustomerId(Long id) {
        return (List<UsagePoint>)this.em.createNamedQuery(UsagePoint.QUERY_FIND_ALL_BY_RETAIL_CUSTOMER_ID)
                .setParameter("retailCustomerId", id)
                .getResultList();
    }

    public UsagePoint findById(Long id) {
        return (UsagePoint)this.em.createNamedQuery(UsagePoint.QUERY_FIND_BY_ID)
                .setParameter("id", id)
                .getSingleResult();
    }

    public void persist(UsagePoint up) {
        this.em.persist(up);
    }

    public void associateByUUID(RetailCustomer retailCustomer, UUID uuid) {
        try {
            UsagePoint existingUsagePoint = findByUUID(uuid);
            existingUsagePoint.setRetailCustomer(retailCustomer);
            persist(existingUsagePoint);
        } catch (NoResultException e) {
            UsagePoint usagePoint = new UsagePoint();
            usagePoint.setUUID(uuid);
            usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));
            usagePoint.setRetailCustomer(retailCustomer);
            persist(usagePoint);
        }
    }

    public void createOrReplaceByUUID(UsagePoint usagePoint) {
        try {
            UsagePoint existingUsagePoint = findByUUID(usagePoint.getUUID());
            usagePoint.setId(existingUsagePoint.getId());
            if (usagePoint.getRetailCustomer() == null) {
                usagePoint.setRetailCustomer(existingUsagePoint.getRetailCustomer());
            }

            if (existingUsagePoint.getMeterReadings() != null) {
                usagePoint.setMeterReadings(existingUsagePoint.getMeterReadings());
            }

            if (existingUsagePoint.getLocalTimeParameters() != null) {
                usagePoint.setLocalTimeParameters(existingUsagePoint.getLocalTimeParameters());
            }

            em.merge(usagePoint);
        } catch (NoResultException e) {
            usagePoint.setPublished(new GregorianCalendar());
            usagePoint.setUpdated(new GregorianCalendar());
            persist(usagePoint);
        }
    }

    @Override
    public List<UsagePoint> findAllUpdatedFor(Subscription subscription) {
        return (List<UsagePoint>)this.em.createNamedQuery(UsagePoint.QUERY_FIND_ALL_UPDATED_FOR)
                .setParameter("lastUpdate", subscription.getLastUpdate())
                .getResultList();
    }

    public void deleteById(Long id) {
        em.remove(findById(id));
    }

    @Override
    public IdentifiedObject findByRelatedHref(String href) {
        return (UsagePoint)this.em.createNamedQuery(UsagePoint.QUERY_FIND_BY_RELATED_HREF)
                .setParameter("href", href)
                .getSingleResult();
    }

    @Override
    public List<Long> findAllIdsForRetailCustomer(Long retailCustomerId) {
        return (List<Long>)this.em.createNamedQuery(UsagePoint.QUERY_FIND_ALL_IDS_FOR_RETAIL_CUSTOMER)
                .setParameter("retailCustomerId", retailCustomerId)
                .getResultList();
    }

    @Override
    public UsagePoint findByURI(String uri) {
        return (UsagePoint)em.createNamedQuery(UsagePoint.QUERY_FIND_BY_URI)
                .setParameter("uri", uri)
                .getSingleResult();
    }

    @Override
    public UsagePoint findByUUID(UUID uuid) {
        return (UsagePoint)this.em.createNamedQuery(UsagePoint.QUERY_FIND_BY_UUID)
                .setParameter("uuid", uuid.toString().toUpperCase())
                .getSingleResult();
    }
}