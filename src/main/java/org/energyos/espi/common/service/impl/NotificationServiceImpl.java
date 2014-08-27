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

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.datatype.XMLGregorianCalendar;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.BatchList;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.NotificationService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author John Teeter
 *
 */
@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Override
    public void notify(Subscription subscription , XMLGregorianCalendar startDate, XMLGregorianCalendar endDate) {
        String thirdPartyNotificationURI = subscription.getApplicationInformation().getThirdPartyNotifyUri();
        String separator = "?";
        String startDateString = "All";
        String endDateString = "All";
        
        if (startDate != null) startDateString =  startDate.toXMLFormat();
        if (endDate != null) endDateString = endDate.toXMLFormat();
        
        String subscriptionURI = subscription.getApplicationInformation().getDataCustodianResourceEndpoint() + "/Batch/Subscription/" + subscription.getId();
        if (startDate != null) {
        	subscriptionURI = subscriptionURI + separator + "published-min=" + startDateString; 
        	separator = "&";
        }
        if (endDate != null) {
        	subscriptionURI = subscriptionURI + separator + "published-max=" + endDateString; 
        }
        
        BatchList batchList = new BatchList();
        batchList.getResources().add(subscriptionURI);
        notifyInternal(thirdPartyNotificationURI, batchList);
    }

	@Override
	public void notify(RetailCustomer retailCustomer,  XMLGregorianCalendar startDate, XMLGregorianCalendar endDate) {
		
		if (retailCustomer != null) {
			
			Subscription subscription = null;

			// find and iterate across all relevant authorizations
			//
			List<Authorization> authorizationList = authorizationService
					.findAllByRetailCustomerId(retailCustomer.getId());
			Iterator<Authorization> authorizationIterator = authorizationList
					.iterator();

			while (authorizationIterator.hasNext()) {
				Authorization authorization = authorizationIterator.next();

				try {
					subscription = subscriptionService.findByAuthorizationId(authorization.getId());
				} catch (Exception e) {
					// an Authorization w/o an associated subscription breaks
					// the propagation chain
					// TODO: if we want to continue the propagation forward, we
					// just need to hook in the subscription substructure

				}
				if (subscription != null) {
					notify (subscription, startDate, endDate);
				}
			}
		}
	}
	
	@Async
	// we want to spawn this to a separate thread so we can get back
	// and commit the actual data import transaction
	private void notifyInternal(String thirdPartyNotificationURI, BatchList batchList) {
		
        try {
            restTemplate.postForLocation(thirdPartyNotificationURI, batchList);
        } catch (Exception e) {
        	// Do nothing
        }		
	}

	@Override
	public void notifyAllNeed() {
	
		List <Long> authList = resourceService.findAllIds(Authorization.class);
		
		Map<Long, BatchList> notifyList = new HashMap<Long, BatchList> ();
		
		for (Long id : authList) {
			
			Authorization authorization = resourceService.findById(id, Authorization.class);
			
			String tempResourceUri = authorization.getResourceURI();
			
			resourceService.findByResourceUri(tempResourceUri, Authorization.class);
			
			System.out.println("resourceURI: " + tempResourceUri);
			
			String thirdParty = authorization.getThirdParty();
			
			// do not do any of the local authorizations
			//
			if (!((thirdParty.equals("data_custodian_admin")) || (thirdParty.equals("upload_admin")))) {
				
				// if this is the first time we have seen this third party, add it to the notification list.
			    if (!(notifyList.containsKey(thirdParty))) {
				    notifyList.put(id, new BatchList ());
			    }
			    
			    // and now add the appropriate resource URIs to the batchList of this third party
			    //
			    String resourceUri = authorization.getResourceURI();
			    
			    // resouceUri's that are just /Batch/Bulk ==> client-access-token and will be ignored here with the 
			    // actual Batch/Bulk ids will be picked up by looking at the scope strings of the individual
			    // authorization/subscription pairs		    
			    if (!(resourceUri.contains("/Batch/Bulk"))) {
			    	String scope = authorization.getScope();
			    	for (String term : scope.split(";")) {
			    	if (term.contains("BR=")) {
			    		// we have a bulkId to deal with
			    		term = term.substring(scope.indexOf("=") + 1);
			    		// TODO the following getResourceURI() should be changed to getBulkRequestURI when the seed tables 
			    		// have non-null values for that attribute.
			    		String bulkResourceUri = authorization.getResourceURI() + "/Batch/Bulk/" + term;
			            if (!(notifyList.get(id).getResources().contains(bulkResourceUri))) {
                          notifyList.get(id).getResources().add(bulkResourceUri);
			            }
			    	  } else {
			    		// just add the resourceUri
			    		  if (!(notifyList.get(id).getResources().contains(resourceUri))) {
			    		        notifyList.get(id).getResources().add(resourceUri);	
			    		  }
			    	  }
			    	}
			    }
			   	
			}
		}
		
		// now notify each ThirdParty
		for (Entry<Long, BatchList> entry : notifyList.entrySet() ) {
			String notifyUri = resourceService.findById(entry.getKey(), Authorization.class).getApplicationInformation().getThirdPartyNotifyUri();
			BatchList batchList = entry.getValue();
			if (!(batchList.getResources().isEmpty())) {
			    notifyInternal(notifyUri, batchList);
			}
		}
	}
	
   public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
   }

   @Override
   public void notify(ApplicationInformation applicationInformation, Long bulkId) {
	String bulkRequestUri = applicationInformation.getDataCustodianBulkRequestURI() + "/" + bulkId;
	String thirdPartyNotificationURI = applicationInformation.getThirdPartyNotifyUri();
    BatchList batchList = new BatchList();
    batchList.getResources().add(bulkRequestUri);
    
    notifyInternal(thirdPartyNotificationURI, batchList);
	
   }

   public RestTemplate getRestTemplate () {
        return this.restTemplate;
   }
   public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
   }

   public ResourceService getResourceService () {
        return this.resourceService;
   }
  
   public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
   }

   public AuthorizationService getAuthorizationService () {
        return this.authorizationService;
   }
   public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
   }

   public SubscriptionService getSubscriptionService () {
        return this.subscriptionService;
   }
   
}
