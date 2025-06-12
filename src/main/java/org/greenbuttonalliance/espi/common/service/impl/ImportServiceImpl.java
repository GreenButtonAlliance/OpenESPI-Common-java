/*
 *
 *    Copyright (c) 2018-2021 Green Button Alliance, Inc.
 *
 *    Portions (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.greenbuttonalliance.espi.common.domain.*;
import org.greenbuttonalliance.espi.common.models.atom.EntryType;
import org.greenbuttonalliance.espi.common.service.*;
import org.greenbuttonalliance.espi.common.utils.ATOMContentHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import jakarta.xml.bind.JAXBContext;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class ImportServiceImpl implements ImportService {

	private final Log logger = LogFactory.getLog(getClass());

	private final Jaxb2Marshaller jaxb2Marshaller;
	private final AuthorizationService authorizationService;
	private final SubscriptionService subscriptionService;
	private final UsagePointService usagePointService;
	private final RetailCustomerService retailCustomerService;
	private final ResourceService resourceService;
	private final EntryProcessorService entryProcessorService;

	public ImportServiceImpl(@Qualifier("domainMarshaller") Jaxb2Marshaller jaxb2Marshaller,
							 AuthorizationService authorizationService,
							 SubscriptionService subscriptionService,
							 UsagePointService usagePointService,
							 RetailCustomerService retailCustomerService,
							 ResourceService resourceService,
							 EntryProcessorService entryProcessorService) {
		this.jaxb2Marshaller = jaxb2Marshaller;
		this.authorizationService = authorizationService;
		this.subscriptionService = subscriptionService;
		this.usagePointService = usagePointService;
		this.retailCustomerService = retailCustomerService;
		this.resourceService = resourceService;
		this.entryProcessorService = entryProcessorService;
	}

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
	public List<EntryType> getEntries() {
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

	@Override
	public void importData(InputStream stream, Long retailCustomerId)
			throws IOException, SAXException, ParserConfigurationException {

		// setup the parser
		JAXBContext context = jaxb2Marshaller.getJaxbContext();

		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		XMLReader reader = factory.newSAXParser().getXMLReader();

		// EntryProcessor processor = new EntryProcessor(resourceLinker, new
		// ResourceConverter(), resourceService);
		ATOMContentHandler atomContentHandler = new ATOMContentHandler(context,
				entryProcessorService);
		reader.setContentHandler(atomContentHandler);

		// do the parse/import

		try {
			reader.parse(new InputSource(stream));

		} catch (SAXException e) {
			if(logger.isErrorEnabled()) {
				logger.error("&nImportServiceImpl -- importData: SAXException&n     Cause = " + e.getClass() + "&n" +
						"     Description = " + e.getMessage() + "&n&n");
			}
			throw new SAXException(e.getMessage(), e);
			
		} catch (Exception e) {
			if(logger.isErrorEnabled()) {
				logger.error("&nImportServiceImpl -- importData:&n     Cause = " + e.getClass() + "&n" +
						"     Description = " + e.getMessage() + "&n&n");
				e.printStackTrace();
			}

			
		}
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
						// we have a conflict in association meaning to Retail
						// Customers
						// TODO: resolve how to handle the conflict mentioned
						// above.
						// TODO: Only works for a single customer and not
						// multiple customers
						retailCustomer = tempRc;
					}
				} else {
					// associate the usagePoint with the Retail Customer
					if (retailCustomer != null) {
						usagePointService.associateByUUID(retailCustomer,
								usagePoint.getUUID());
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
					subscription = subscriptionService
							.findByAuthorizationId(authorization.getId());
				} catch (Exception e) {
					// an Authorization w/o an associated subscription breaks
					// the propagation chain
					if(logger.isErrorEnabled()) {
						logger.error("**** End of Notification Propagation Chain&n");
					}
				}
				if (subscription != null) {
					String resourceUri = authorization.getResourceURI();
					// this is the first time this authorization has been in
					// effect. We must set up the appropriate resource links
					if (resourceUri == null) {
						ApplicationInformation applicationInformation = authorization
								.getApplicationInformation();
						resourceUri = applicationInformation
								.getDataCustodianResourceEndpoint();
						resourceUri = resourceUri + "/Batch/Subscription/"
								+ subscription.getId();
						authorization.setResourceURI(resourceUri);

						resourceService.merge(authorization);
					}

					// make sure the UsagePoint(s) we just imported are linked
					// up
					// with
					// the Subscription

					for (UsagePoint usagePoint : usagePointList) {
						boolean addNew = false;
						for (UsagePoint up : subscription.getUsagePoints()) {
							if (up.equals(usagePoint))
								addNew = true;
						}

						if (addNew)
							subscriptionService.addUsagePoint(subscription,
									usagePoint);

					}
				}
			}
		}
	}


}
