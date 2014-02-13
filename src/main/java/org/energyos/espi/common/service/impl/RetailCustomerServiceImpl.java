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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.ServiceCategory;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.RetailCustomerRepository;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.service.UsagePointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RetailCustomerServiceImpl implements RetailCustomerService {

    @Autowired
    private RetailCustomerRepository retailCustomerRepository;
    
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private ImportService importService;
    
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    UsagePointService usagePointService;
    
    public void setResourceService(ResourceService resourceService){
    	this.resourceService = resourceService;
    }

    public void setImportService(ImportService importService){
    	this.importService = importService;
    }

    public void setRepository(RetailCustomerRepository retailCustomerRepository) {
        this.retailCustomerRepository = retailCustomerRepository;
    }
    
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public List<RetailCustomer> findAll() {
        return retailCustomerRepository.findAll();
    }

    @Override
    public void persist(RetailCustomer customer) {
    	retailCustomerRepository.persist(customer);
    }

    @Override
    public RetailCustomer findById(Long id) {
        return retailCustomerRepository.findById(id);
    }
    
	@Override
	public RetailCustomer findById(String retailCustomerId) {
		return retailCustomerRepository.findById(retailCustomerId);
	}
	
    @Override
    public RetailCustomer findByHashedId(Long retailCustomerId) {
        return findById(retailCustomerId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return retailCustomerRepository.findByUsername(username);
        } catch (EmptyResultDataAccessException x) {
            throw new UsernameNotFoundException("Unable to find user");
        }
    }
    
	@Override
	public void add(RetailCustomer retailCustomer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(RetailCustomer retailCustomer) {
		retailCustomerRepository.deleteById(retailCustomer.getId());
		
	}

	@Override
	public RetailCustomer importResource(InputStream stream) {
		RetailCustomer retailCustomer = null;
		try {
			importService.importData(stream, null);
			EntryType entry = importService.getEntries().get(0);
			// TODO - add RetailCustomer to the entrytype structure
//			RetailCustomer retailCustomer = entry.getContent().getRetailCustomer();
//			persist(retailCustomer);
		} catch (Exception e) {

		}
		return retailCustomer;
	}

	@Transactional
	@Override
	public Subscription associateByUUID(Long retailCustomerId, UUID uuId, String description) {
        Subscription subscription = null;
        UsagePoint usagePoint = new UsagePoint();
        usagePoint.setUUID(uuId);
        usagePoint.setDescription(description);

        RetailCustomer retailCustomer = findById(retailCustomerId);
        usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE));
        usagePoint.setRetailCustomer(retailCustomer);
        usagePointService.createOrReplaceByUUID(usagePoint);
        
        // now retrieve the result and use it for any pending subscriptions
        usagePoint = usagePointService.findByUUID(uuId);
        
        // now see if there are any authorizations for this information
        //
        try {
        
        	List<Authorization> authorizationList = authorizationService.findAllByRetailCustomerId(retailCustomer.getId());
        	Iterator<Authorization> authorizationIterator = authorizationList.iterator();
        
        	while (authorizationIterator.hasNext()) {
        	
        		Authorization authorization = authorizationIterator.next();
        		subscription = subscriptionService.findByAuthorizationId(authorization.getId()); 
        		String resourceUri = authorization.getResourceURI();
        		if (resourceUri == null) {
			
        			// this is the first time this authorization has been in effect. We
        			// must set up the appropriate resource links
        			ApplicationInformation applicationInformation = authorization.getApplicationInformation();
        			resourceUri = applicationInformation.getDataCustodianResourceEndpoint();
        			resourceUri = resourceUri + "/Batch/Subscription/" + subscription.getId();	
        			authorization.setResourceURI(resourceUri);
        		}

        		// make sure the UsagePoints we just imported are linked up with
        		//  the subscription if any
        		subscription = subscriptionService.addUsagePoint(subscription, usagePoint);
        		resourceService.persist(subscription);
        		resourceService.persist(usagePoint);
        	}      	
	      } catch (Exception e){
	    	  // we don't expect any problems here, and if we do have an exception,
	    	  // it will rollback the transaction.
	    	  e.printStackTrace();
	    	  return null;
	      }
        
        return subscription;
	}


}
