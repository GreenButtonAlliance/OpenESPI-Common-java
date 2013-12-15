package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.utils.DateConverter;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SubscriptionServiceImpl implements SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private ApplicationInformationService applicationInformationService;

    @Autowired
    private ResourceService resourceService;

    @Override
    public Subscription createSubscription(OAuth2Authentication authentication) {
        Subscription subscription = new Subscription();
        subscription.setUUID(UUID.randomUUID());
        subscription.setApplicationInformation(applicationInformationService.findByClientId(authentication.getOAuth2Request().getClientId()));
        subscription.setRetailCustomer((RetailCustomer)authentication.getPrincipal());
        subscription.setLastUpdate(DateConverter.epoch());
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
    public Subscription findByHashedId(String hashedId) {
        return  subscriptionRepository.findByHashedId(hashedId);
    }

    @Override
    public EntryTypeIterator findEntriesByHashedId(String hashedId) {
    	Subscription subscription = subscriptionRepository.findByHashedId(hashedId);
    	List<Long> subscriptionIds = new ArrayList<Long>();
    	subscriptionIds.add(subscription.getId());
        return new EntryTypeIterator(resourceService, subscriptionIds);
    }

    private List<Long> findAllIdsForRetailCustomer(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public EntryTypeIterator findEntriesByRetailCustomerId(Long retailCustomerId) {
        List<Long> allIdsForRetailCustomer = findAllIdsForRetailCustomer(retailCustomerId);

        return new EntryTypeIterator(resourceService, allIdsForRetailCustomer);
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryType find(Long retailCustomerId, Long subscriptionId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntryTypeIterator find(Long retailCustomerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Subscription subscription) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Subscription subscription) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Subscription importResource(InputStream stream) {
		// TODO Auto-generated method stub
		return null;
	}
	private List<Long> findAllIdsByRetailCustomer(String retailCustomerId){ 
		 List<Long> result = new ArrayList<Long>();
		 Subscription subscription = subscriptionRepository.findByHashedId(retailCustomerId);
		 result.add(subscription.getId());
		 return result;
		
	}
}
