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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.EntryProcessorService;
import org.energyos.espi.common.service.ImportService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.utils.ATOMContentHandler;
//import org.energyos.espi.common.utils.EntryProcessor;
//import org.energyos.espi.common.utils.ResourceConverter;
//import org.energyos.espi.common.utils.ResourceLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

@Service

public class ImportServiceImpl implements ImportService {
    @Autowired
    @Qualifier("atomMarshaller")
    private Jaxb2Marshaller marshaller;
    
    @Autowired
    private AuthorizationService authorizationService; 
    
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private UsagePointService usagePointService;
    
    @Autowired
    private RetailCustomerService retailCustomerService;
    
    @Autowired
    private ResourceService resourceService;
    
    @Autowired
    private EntryProcessorService entryProcessorService;
    
    // this is a list of the UsagePointIds referenced during
    // this import
    private List<EntryType> entries;
    
    // Min Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar minUpdated = null;
    
    // Max Updated <== used on time scoping the subscriptions
    //
    private XMLGregorianCalendar maxUpdated = null;
    
    @Override
    public List <EntryType> getEntries() {
    	List<EntryType> result = entries;
    	entries = null;
    	return result;
    }

    @Override
    public XMLGregorianCalendar getMinUpdated() {
    	return this.minUpdated;
    }
    
    @Override
    public XMLGregorianCalendar getMaxUpdated() {
    	return this.maxUpdated;
    }
    
    // @Transactional (rollbackFor= {javax.xml.bind.JAXBException.class}, 
    //            noRollbackFor = {javax.persistence.NoResultException.class, org.springframework.dao.EmptyResultDataAccessException.class })
    @Override
    public void importData(InputStream stream, Long retailCustomerId) throws IOException, SAXException, ParserConfigurationException {
        
        // setup the parser                                                                                                                                  
	    JAXBContext context = marshaller.getJaxbContext();

        SAXParserFactory factory = SAXParserFactory.newInstance();
	    factory.setNamespaceAware(true);
        XMLReader reader = factory.newSAXParser().getXMLReader();

	    // EntryProcessor processor = new EntryProcessor(resourceLinker, new ResourceConverter(), resourceService);
        ATOMContentHandler atomContentHandler = new ATOMContentHandler(context, entryProcessorService);
        reader.setContentHandler(atomContentHandler);

        // do the parse/import

        reader.parse(new InputSource(stream));

        // context of the import used for linking things up
        // and establishing notifications
        //

        entries = atomContentHandler.getEntries();
        minUpdated = atomContentHandler.getMinUpdated();     
        maxUpdated = atomContentHandler.getMaxUpdated();
       
        // cleanup/end processing
        // 1 - associate to usage points to the right retail customer
        // 2 - make sure authorization/subscriptions have the right URIs
        // 3 - place the imported usagePoints in to the subscriptions 
        //
		List<UsagePoint> usagePointList = new ArrayList<UsagePoint>();
		
		// now perform any associations (to RetailCustomer) and stage the
		// Notifications (if any)
		
		RetailCustomer retailCustomer = null;
		
		if (retailCustomerId != null) {
		   retailCustomer = retailCustomerService.findById(retailCustomerId);
		}

		Iterator<EntryType> its = entries.iterator();

		while (its.hasNext()) {
			EntryType entry = its.next();
			UsagePoint usagePoint = entry.getContent().getUsagePoint();
			if (usagePoint != null) {
				
				// see if we already have a retail customer association
				
				RetailCustomer tempRc = usagePoint.getRetailCustomer();
				if (tempRc != null) {
					// hook it to the retailCustomer
					if (!(tempRc.equals(retailCustomer))) {
						// we have a conflict in association meaning to Retail Customers
						// TODO: resolve how to handle the conflict mentioned above.
					}
				} else {
					// associate the usagePoint with the Retail Customer
					if (retailCustomer != null) {
						usagePointService.associateByUUID(retailCustomer, usagePoint.getUUID());
					}
				}
			    usagePointList.add(usagePoint);
			}
		}

		// now if we have a retail customer, check for any subscriptions that
		// need associated
		if (retailCustomer != null) {

			Subscription subscription = null;

			// find and iterate across all relevant authorizations
			//
			List<Authorization> authorizationList = authorizationService
					.findAllByRetailCustomerId(retailCustomer.getId());
			for (Authorization authorization : authorizationList) {
	
				try {
					subscription = subscriptionService.findByAuthorizationId(authorization.getId());
				} catch (Exception e) {
					// an Authorization w/o an associated subscription breaks
					// the propagation chain
					System.out.printf("**** End of Notification Propgation Chain\n");
				}
				if (subscription != null) {
					String resourceUri = authorization.getResourceURI();
					// this is the first time this authorization has been in
					// effect.  We must set up the appropriate resource links
					if (resourceUri == null) {
						ApplicationInformation applicationInformation = authorization.getApplicationInformation();
						resourceUri = applicationInformation.getDataCustodianResourceEndpoint();
						resourceUri = resourceUri + "/Batch/Subscription/" + subscription.getId();
						authorization.setResourceURI(resourceUri);
						
						resourceService.merge(authorization);
					}

					// make sure the UsagePoint(s) we just imported are linked up
					// with
					// the Subscription

					for (UsagePoint usagePoint : usagePointList) {
						
						if (!(subscription.getUsagePoints().contains(usagePoint))) {
							
                            subscriptionService.addUsagePoint(subscription, usagePoint);
							
						}
					}
				}
			}
		}
    }
        
    public void setResourceService(ResourceService resourceService) {
        this.resourceService = resourceService;
    }
    
    public void setAuthorizationService(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }
    
    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }
    
    public void setUsagePointService(UsagePointService usagePointService) {
        this.usagePointService = usagePointService;
    }
    
    public void setRetailCustomerService(RetailCustomerService retailCustomerService) {
        this.retailCustomerService = retailCustomerService;
    }  
    
    public void setEntryProcessorService (EntryProcessorService entryProcessorService) {
    	this.entryProcessorService = entryProcessorService;
    }

}
