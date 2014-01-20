package org.energyos.espi.common.service.impl;

import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.IdentifiedObject;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.SubscriptionRepository;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.ExportService;
import org.energyos.espi.common.service.ImportService;
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
import java.util.Iterator;
import java.util.GregorianCalendar;
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

	private ImportService importService;
	
	private ExportService exportService;
	
	public void setImportService (ImportService importService) {
		this.importService = importService;
	}

	public void setExportService (ExportService exportService) {
		this.exportService = exportService;
	}
	
    @Override
    public Subscription createSubscription(OAuth2Authentication authentication) {
        Subscription subscription = new Subscription();
        subscription.setUUID(UUID.randomUUID());
        subscription.setApplicationInformation(applicationInformationService.findByClientId(authentication.getOAuth2Request().getClientId()));
        subscription.setRetailCustomer((RetailCustomer)authentication.getPrincipal());
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
        return new EntryTypeIterator(resourceService, subscriptionIds);
    }

    private List<Long> findAllIdsForRetailCustomer(Long id) {
		// TODO Auto-generated method stub
		return null;
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
			result = (new EntryTypeIterator(resourceService, allIds)).nextEntry(Subscription.class);
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
			result = (new EntryTypeIterator(resourceService, temp));
		} catch (Exception e) {
			// TODO need a log file entry as we are going to return a null if
			// it's not found
			result = null;
		}
		return result;	
	}

	@Override
	public void add(Subscription subscription) {
		// TODO Auto-generated method stub
		
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
			importService.importData(stream);
			EntryType entry = importService.getEntries().get(0);
			Subscription subscription = entry.getContent().getSubscription();
			return subscription;
		} catch (Exception e) {
			return null;
		}
	}
	private List<Long> findAllIdsByRetailCustomer(String retailCustomerId){ 
		 List<Long> result = new ArrayList<Long>();
		 Subscription subscription = subscriptionRepository.findByHashedId(retailCustomerId);
		 result.add(subscription.getId());
		 return result;
		
	}

	@Override
	public Subscription findById(Long subscriptionId) {
		return subscriptionRepository.findById(subscriptionId);
	}

	@Override
	public Subscription findById(Long retailCustomerId, Long subscriptionId) {
		return subscriptionRepository.findById(subscriptionId);
	}

}
