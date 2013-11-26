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

package org.energyos.espi.common.service.impl;

import com.sun.syndication.io.FeedException;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.utils.ATOMMarshaller;
import org.energyos.espi.common.utils.SubscriptionBuilder;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.energyos.espi.common.utils.XMLMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UsagePointServiceImpl implements UsagePointService {

    @Autowired
    private XMLMarshaller xmlMarshaller;
    @Autowired
    private UsagePointRepository repository;
    @Autowired
    private ATOMMarshaller marshaller;
    @Autowired
    private UsagePointBuilder usagePointBuilder;
    @Autowired
    private SubscriptionBuilder subscriptionBuilder;

    public void setRepository(UsagePointRepository repository) {
        this.repository = repository;
    }

    public void setMarshaller(ATOMMarshaller marshaller) {
        this.marshaller = marshaller;
    }

    public void setXMLMarshaller(XMLMarshaller xmlMarshaller) {
        this.xmlMarshaller = xmlMarshaller;
    }

    public void setUsagePointBuilder(UsagePointBuilder usagePointBuilder) {
        this.usagePointBuilder = usagePointBuilder;
    }

    public void setSubscriptionBuilder(SubscriptionBuilder subscriptionBuilder) {
        this.subscriptionBuilder = subscriptionBuilder;
    }

    @Override
    public List<UsagePoint> findAllByRetailCustomer(RetailCustomer customer) {
        return repository.findAllByRetailCustomerId(customer.getId());
    }

    @Override
    public UsagePoint findById(Long id) {
        return this.repository.findById(id);
    }

    @Override
    public void persist(UsagePoint up) {
        this.repository.persist(up);
    }

    @Override
    public UsagePoint importUsagePoint(InputStream stream) {
        UsagePoint usagePoint = usagePointBuilder.newUsagePoint(xmlMarshaller.unmarshal(stream, EntryType.class));
        createOrReplaceByUUID(usagePoint);

        return usagePoint;
    }

    @Override
    public void createOrReplaceByUUID(UsagePoint usagePoint) {
        repository.createOrReplaceByUUID(usagePoint);
    }

    @Override
    public String exportUsagePoints(RetailCustomer customer) throws FeedException {
        return marshaller.marshal(subscriptionBuilder.buildFeed(findAllByRetailCustomer(customer)));
    }

    @Override
    public String exportUsagePointById(Long usagePointId) throws FeedException {
        List<UsagePoint> usagePointList = new ArrayList<>();
        usagePointList.add(findById(usagePointId));

        return marshaller.marshal(subscriptionBuilder.buildFeed(usagePointList));
    }

    @Override
    public void associateByUUID(RetailCustomer retailCustomer, UUID uuid) {
        repository.associateByUUID(retailCustomer, uuid);
    }

    @Override
    public UsagePoint findByUUID(UUID uuid) {
        return repository.findByUUID(uuid);
    }

    @Override
    public UsagePoint findByHashedId(String usagePointHashedId) {
        return findByUUID(UUID.fromString(usagePointHashedId));
    }

    @Override
    public List<UsagePoint> findAllUpdatedFor(Subscription subscription) {
        return repository.findAllUpdatedFor(subscription);
    }

    @Override
    public void deleteByHashedId(String usagePointHashedId) {
        UsagePoint usagePoint = findByHashedId(usagePointHashedId);
        if (usagePoint != null) {
            repository.deleteById(usagePoint.getId());
        }
    }

    @Override
    public List<Long> findAllIdsForRetailCustomer(Long retailCustomerId) {
        return repository.findAllIdsForRetailCustomer(retailCustomerId);
    }
}
