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

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.utils.ATOMMarshaller;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.energyos.espi.common.utils.SubscriptionBuilder;
import org.energyos.espi.common.utils.UsagePointBuilder;
import org.energyos.espi.common.utils.XMLMarshaller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UsagePointServiceImpl implements UsagePointService {

    @Autowired
    private XMLMarshaller xmlMarshaller;
    @Autowired
    private UsagePointRepository usagePointRepository;
    @Autowired
    private ATOMMarshaller marshaller;
    @Autowired
    private UsagePointBuilder usagePointBuilder;
    @Autowired
    private SubscriptionBuilder subscriptionBuilder;

    public void setRepository(UsagePointRepository usagePointRepository) {
        this.usagePointRepository = usagePointRepository;
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
        return usagePointRepository.findAllByRetailCustomerId(customer.getId());
    }

    @Override
    public UsagePoint findById(Long usagePointId) {
        return this.usagePointRepository.findById(usagePointId);
    }

    @Override
    public UsagePoint findById(Long retailCustomerId, Long usagePointId) {
    	// TODO: if needed, this needs to be scoped down to the RetailCustomer collection
    	return this.usagePointRepository.findById(usagePointId);
    }
    @Override
    public void persist(UsagePoint up) {
        this.usagePointRepository.persist(up);
    }

    @Override
    public void createOrReplaceByUUID(UsagePoint usagePoint) {
    	usagePointRepository.createOrReplaceByUUID(usagePoint);
    }

    @Override
    public void associateByUUID(RetailCustomer retailCustomer, UUID uuid) {
    	usagePointRepository.associateByUUID(retailCustomer, uuid);
    }

    @Override
    public UsagePoint findByUUID(UUID uuid) {
        return usagePointRepository.findByUUID(uuid);
    }

    @Override
    public UsagePoint findByHashedId(String usagePointHashedId) {
        return findByUUID(UUID.fromString(usagePointHashedId));
    }

    @Override
    public List<UsagePoint> findAllUpdatedFor(Subscription subscription) {
        return usagePointRepository.findAllUpdatedFor(subscription);
    }

    @Override
    public void deleteByHashedId(String usagePointHashedId) {
        UsagePoint usagePoint = findByHashedId(usagePointHashedId);
        if (usagePoint != null) {
        	usagePointRepository.deleteById(usagePoint.getId());
        }
    }

    @Override
    public List<Long> findAllIdsForRetailCustomer(Long retailCustomerId) {
        return usagePointRepository.findAllIdsForRetailCustomer(retailCustomerId);
    }

	@Override
	public String feedFor(List<UsagePoint> usagePoints) throws FeedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String entryFor(UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override
	public List<UsagePoint> findAllByRetailCustomer(Long retailCustomerId) {
     return usagePointRepository.findAllByRetailCustomerId(retailCustomerId);
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long retailCustomerId, Long usagePointId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UsagePoint usagePoint) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UsagePoint importResource(InputStream stream) {
        UsagePoint usagePoint = usagePointBuilder.newUsagePoint(xmlMarshaller.unmarshal(stream, EntryType.class));
        createOrReplaceByUUID(usagePoint);

        return usagePoint;
	}

}
