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

package org.energyos.espi.common.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.repositories.UsagePointRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.utils.EntryTypeIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    
    @Autowired
    private UsagePointRepository usagePointRepository;

    @Autowired
    private ApplicationInformationService applicationInformationService;

    @Autowired
    private ResourceService resourceService;

	private ImportService importService;
	
	public void setImportService (ImportService importService) {
		this.importService = importService;
	}

	@Override
	@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

    public Subscription createSubscription(OAuth2Authentication authentication) {
        Subscription subscription = new Subscription();
        subscription.setUUID(UUID.randomUUID());
        subscription.setApplicationInformation(applicationInformationService.findByClientId(authentication.getOAuth2Request().getClientId()));
        subscription.setRetailCustomer((RetailCustomer)authentication.getPrincipal());
        subscription.setUsagePoints(new ArrayList<UsagePoint> ());
        // set up the subscription's usagePoints list. Keep in mind that right 
        // now this is ALL usage points belonging to the RetailCustomer. 
        // TODO - scope this to only a selected (proper) subset of the usagePoints as passed 
        // through from the UX or a restful call.
        List <Long> upIds = resourceService.findAllIdsByXPath(subscription.getRetailCustomer().getId(), UsagePoint.class);
        Iterator <Long> it = upIds.iterator();
        while (it.hasNext()) {
        	UsagePoint usagePoint = resourceService.findById(it.next(), UsagePoint.class);
        	subscription.getUsagePoints().add(usagePoint);
        }
        subscription.setLastUpdate(new GregorianCalendar());
        subscriptionRepository.persist(subscription);

        return subscription;
    }

    @Override
    public List<Subscription> findAll() {
        return  subscriptionRepository.findAll();
    }

    @Override
    public void persist(Subscription subscription) {
    	 subscriptionRepository.persist(subscription);
    }

    @Override
    public Subscription findByUUID(UUID uuid) {
        return subscriptionRepository.findByUUID(uuid);
    }
    
    @Override
    public Subscription findByHashedId(String hashedId) {
        return  subscriptionRepository.findByHashedId(hashedId);
    }

    @Override
    public EntryTypeIterator findEntriesByHashedId(String hashedId) {
    	Subscription subscription = subscriptionRepository.findByHashedId(hashedId);
    	List<Long> subscriptionIds = new ArrayList<Long>();
    	subscriptionIds.add(subscription.getId());
        return new EntryTypeIterator(resourceService, subscriptionIds, Subscription.class);
    }

    public void setRepository(SubscriptionRepository  subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public void setApplicationInformationService(ApplicationInformationService applicationInformationService) {
    	this.applicationInformationService = applicationInformationService;
    }
    
    public void setSubscriptionRepository(SubscriptionRepository subscriptionRepository) {
    	this.subscriptionRepository = subscriptionRepository;
    }
    
	@Override
	public EntryTypeIterator find(String subscriptionHashedId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Subscription findById(String subscriptionId) {
	return subscriptionRepository.findByHashedId(subscriptionId);
		
	}

	@Override
	public EntryType findEntryType(Long retailCustomerId, Long subscriptionId) {
		EntryType result = null;
		try {
			List<Long> allIds = new ArrayList<Long>();
			allIds.add(subscriptionId);
			result = (new EntryTypeIterator(resourceService, allIds, Subscription.class)).nextEntry(Subscription.class);
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;
	}

	@Override
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId) {
		EntryTypeIterator result = null;
		try {
			// TODO - this is sub-optimal (but defers the need to understand creation of an EntryType
			List<Long> temp = new ArrayList<Long>();
			temp = resourceService.findAllIds(Subscription.class);
			result = (new EntryTypeIterator(resourceService, temp, Subscription.class));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;	
	}

	@Override
	public void delete(Subscription subscription) {
		subscriptionRepository.deleteById(subscription.getId());	
	}
	
	@Override
	public void merge(Subscription subscription){
		subscriptionRepository.merge(subscription);
	}

	@Override
	public Subscription importResource(InputStream stream) {
		try {
			importService.importData(stream, null);
			EntryType entry = importService.getEntries().get(0);
			Subscription subscription = entry.getContent().getSubscription();
			return subscription;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Subscription findById(Long subscriptionId) {
		return subscriptionRepository.findById(subscriptionId);
	}

	@Override
	public Subscription findById(Long retailCustomerId, Long subscriptionId) {
		return subscriptionRepository.findById(subscriptionId);
	}

	@Override
	public List<Long> findUsagePointIds(Long subscriptionId) {
		
		List<Long> result = new ArrayList<Long>();
		Subscription subscription = subscriptionRepository.findById(subscriptionId);
		Iterator<UsagePoint> it = subscription.getUsagePoints().iterator();
		while (it.hasNext()) {
			result.add(it.next().getId());
		}

		return result;
	}

	@Override
	public Subscription findByAuthorizationId(Long id) {
       return subscriptionRepository.findByAuthorizationId(id);
	}

	@Override
	@Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
                noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })

	public Subscription addUsagePoint(Subscription subscription, UsagePoint usagePoint) {
		
		subscription.getUsagePoints().add(usagePoint);
		return subscription;
		
	}

}
