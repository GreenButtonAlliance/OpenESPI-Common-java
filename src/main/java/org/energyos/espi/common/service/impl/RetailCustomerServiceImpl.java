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
			// TODO - Make RetailCustomer inherit from IdentifiedObject and add RetailCustomer to the entrytype structure

			// EntryType entry = importService.getEntries().get(0);
            // RetailCustomer retailCustomer = entry.getContent().getRetailCustomer();
            // persist(retailCustomer);
		} catch (Exception e) {
          System.out.printf("**** RetailCustomerService:importResource Failed %s\n", e.toString());
		}
		return retailCustomer;
	}

	@Transactional
	@Override
	public Subscription associateByUUID(Long retailCustomerId, UUID uuid) {
        Subscription subscription = null;
        RetailCustomer retailCustomer = null;
        UsagePoint usagePoint = null;
        
       try {
    	   retailCustomer = resourceService.findById(retailCustomerId, RetailCustomer.class);
           try {
        	   
    	       usagePoint = resourceService.findByUUID(uuid, UsagePoint.class);
    	       
           } catch (Exception e) {
        	   
        	   usagePoint = new UsagePoint();
        	   usagePoint.setUUID(uuid);
        	   usagePoint.setDescription("A Temporary UsagePoint Description");
        	   usagePoint.setServiceCategory(new ServiceCategory(ServiceCategory.ELECTRICITY_SERVICE))	;
        	   resourceService.persist(usagePoint);
        	   
           }
           usagePoint.setRetailCustomer(retailCustomer);
           resourceService.merge(usagePoint);
           

           // now see if there are any authorizations for this information
           //
           try {
           
           	for (Authorization authorization : authorizationService.findAllByRetailCustomerId(retailCustomer.getId())) {
 
 
           		String resourceUri = authorization.getResourceURI();
           		if (resourceUri == null) {
 
           			authorization.setResourceURI(authorization.getApplicationInformation().getDataCustodianResourceEndpoint()
           					+ "/Batch/Subscription/" + subscription.getId());	
           			resourceService.merge(authorization);
           			
           		}

           		subscription = subscriptionService.findByAuthorizationId(authorization.getId());

           		subscription.getUsagePoints().add(usagePoint);
           		resourceService.merge(subscription);
           	}      	
   	      } catch (Exception e){
              // we get here if we don't have a subscription
   	    	  return null;
   	      }
           
      
        } catch (Exception e) {
        	System.out.printf("****Error Associating UsagePoint: %s - %s\n", retailCustomer.toString(), usagePoint.toString());
        }
     
        return subscription;
	}


}
