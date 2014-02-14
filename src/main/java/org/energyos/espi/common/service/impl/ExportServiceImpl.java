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
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.xml.transform.stream.StreamResult;

import org.energyos.espi.common.domain.ApplicationInformation;
import org.energyos.espi.common.domain.Authorization;
import org.energyos.espi.common.domain.ElectricPowerQualitySummary;
import org.energyos.espi.common.domain.ElectricPowerUsageSummary;
import org.energyos.espi.common.domain.IntervalBlock;
import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.ReadingType;
import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.Subscription;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.DateTimeType;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.ApplicationInformationService;
import org.energyos.espi.common.service.AuthorizationService;
import org.energyos.espi.common.service.ElectricPowerQualitySummaryService;
import org.energyos.espi.common.service.ElectricPowerUsageSummaryService;
import org.energyos.espi.common.service.ExportService;
import org.energyos.espi.common.service.IntervalBlockService;
import org.energyos.espi.common.service.MeterReadingService;
import org.energyos.espi.common.service.ReadingTypeService;
import org.energyos.espi.common.service.ResourceService;
import org.energyos.espi.common.service.RetailCustomerService;
import org.energyos.espi.common.service.SubscriptionService;
import org.energyos.espi.common.service.TimeConfigurationService;
import org.energyos.espi.common.service.UsagePointService;
import org.energyos.espi.common.utils.AtomMarshallerListener;
import org.energyos.espi.common.utils.DateConverter;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

@Service
public class ExportServiceImpl implements ExportService {

	@Autowired
	private ResourceService resourceService;
	
    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private RetailCustomerService retailCustomerService;
    
    @Autowired
    private UsagePointService usagePointService;
    
    @Autowired
    private MeterReadingService meterReadingService;
    
    @Autowired
    private ReadingTypeService readingTypeService;
    
    @Autowired
    private IntervalBlockService intervalBlockService;
    
    @Autowired
    private ElectricPowerQualitySummaryService electricPowerQualitySummaryService;
    
    @Autowired
    private ElectricPowerUsageSummaryService electricPowerUsageSummaryService;
    
    @Autowired
    private AuthorizationService authorizationService;
    
    @Autowired
    private ApplicationInformationService applicationInformationService;
    
    @Autowired
    private TimeConfigurationService timeConfigurationService;
    
    @Autowired
    @Qualifier("fragmentMarshaller")
    private Jaxb2Marshaller fragmentMarshaller;
    
    // setup the services
    //
    
    public void setResourceService(ResourceService resourceService) {
    	this.resourceService = resourceService;
    }
    
    public void setSubscriptionService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public void setAuthorization(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    public void setApplicationInformationService(ApplicationInformationService applicationInformationService) {
    	this.applicationInformationService = applicationInformationService;
    }
     
 
    public void setRetailCustomerService (RetailCustomerService retailCustomerService) {
    	this.retailCustomerService = retailCustomerService;
    }
    
    public void setUsagePointService(UsagePointService usagePointService) {
    	this.usagePointService = usagePointService;
    }
    
    public void setMeterReadingService(MeterReadingService meterReadingService) {
    	this.meterReadingService = meterReadingService;
    }
    
    public void setReadingTypeService(ReadingTypeService readingTypeService) {
    	this.readingTypeService = readingTypeService;
    }
    
    public void setIntervalBlockService(IntervalBlockService intervalBlockService) {
    	this.intervalBlockService = intervalBlockService;
    }
    
    public void setTimeConfigurationService(TimeConfigurationService timeConfigurationService) {
    	this.timeConfigurationService = timeConfigurationService;
    }
        
    public void setElectricPowerQualitySummaryService(ElectricPowerQualitySummaryService electricPowerQualitySummaryService) {
    	this.electricPowerQualitySummaryService = electricPowerQualitySummaryService;
    }
     
    public void setElectricPowerUsageSummaryService(ElectricPowerUsageSummaryService electricPowerUsageSummaryService) {
    	this.electricPowerUsageSummaryService = electricPowerUsageSummaryService;
    }
    
    
    public void setMarshaller(Jaxb2Marshaller fragmentMarshaller) {
        this.fragmentMarshaller = fragmentMarshaller;
    }
    
    // TODO Convert this block of functions to a Template system
    //
    
    // ApplicationInformation

    // - ROOT form
	@Override
	public void exportApplicationInformation(Long applicationInformationId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ApplicationInformation/" + applicationInformationId;
		exportEntry(resourceService.findEntryType(applicationInformationId, ApplicationInformation.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportApplicationInformations(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ApplicationInformation";
		exportEntries(resourceService.findEntryTypeIterator(ApplicationInformation.class), stream, exportFilter, ApplicationInformation.class, hrefFragment);		
	}

    // Authorization

    // - ROOT form
	@Override
	public void exportAuthorization(Long authorizationId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Authorization/" + authorizationId;
		exportEntry(resourceService.findEntryType(authorizationId, Authorization.class), stream, exportFilter, hrefFragment);			
	}

	@Override
	public void exportAuthorizations(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Authorization";
		exportEntries(resourceService.findEntryTypeIterator(Authorization.class), stream, exportFilter, Authorization.class, hrefFragment);		
	}

    // - XPath form
	@Override
	public void exportAuthorization(Long retailCustomerId, Long authorizationId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/Authorization/" + authorizationId;
		exportEntry(authorizationService.findEntryType(retailCustomerId, authorizationId), stream, exportFilter, hrefFragment);			
	}

	@Override
	public void exportAuthorizations(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/Authorization";
		exportEntries(authorizationService.findEntryTypeIterator(retailCustomerId), stream, exportFilter, Authorization.class, hrefFragment);		
	}

    // ElectricPowerQualitySummary

    // - ROOT form
	@Override
	public void exportElectricPowerQualitySummary(Long electricPowerQualitySummaryId, OutputStream stream, ExportFilter exportFilter) throws IOException {
	    String hrefFragment = "/ElectricPowerQualitySummary/" + electricPowerQualitySummaryId;
		exportEntry(resourceService.findEntryType(electricPowerQualitySummaryId, ElectricPowerQualitySummary.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportElectricPowerQualitySummarys(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ElectricPowerQualitySummary";
	    exportEntries(resourceService.findEntryTypeIterator(ElectricPowerQualitySummary.class), stream, exportFilter, ElectricPowerQualitySummary.class, hrefFragment);	
	}

    // - XPath form
	@Override
	public void exportElectricPowerQualitySummary(Long retailCustomerId, Long usagePointId, Long electricPowerQualitySummaryId,
						      OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer" + retailCustomerId + "/UsagePoint/" + usagePointId + "/ElectricPowerQualitySummary/" + electricPowerQualitySummaryId;
		exportEntry(electricPowerQualitySummaryService.findEntryType(retailCustomerId, usagePointId, electricPowerQualitySummaryId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportElectricPowerQualitySummarys(Long retailCustomerId, Long usagePointId,
						       OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer" + retailCustomerId + "/UsagePoint/" + usagePointId + "/ElectricPowerQualitySummary";
		exportEntries(electricPowerQualitySummaryService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, ElectricPowerQualitySummary.class,  hrefFragment);	
	}

    // ElectricPowerUsageSummary

    // - ROOT form
	@Override
	public void exportElectricPowerUsageSummarys(ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException {
	    String hrefFragment = "/ElectricPowerUsageSummary";
		exportEntries(resourceService.findEntryTypeIterator(ElectricPowerUsageSummary.class), outputStream, exportFilter, ElectricPowerQualitySummary.class, hrefFragment);	
	}

	@Override
	public void exportElectricPowerUsageSummary(long electricPowerUsageSummaryId, ServletOutputStream outputStream,	ExportFilter exportFilter) throws IOException {
	    String hrefFragment = "/ElectricPowerUsageSummary/" + electricPowerUsageSummaryId;
		exportEntry(resourceService.findEntryType(electricPowerUsageSummaryId, ElectricPowerUsageSummary.class), outputStream, exportFilter, hrefFragment);
	}


    // - XPath form
	@Override
	public void exportElectricPowerUsageSummary(Long retailCustomerId, Long usagePointId, Long electricPowerUsageSummaryId,
						    OutputStream stream, ExportFilter exportFilter) throws IOException {
	    String hrefFragment = "/RetailCustomer/"+ retailCustomerId + "/UsagePoint/" + usagePointId + "/ElectricPowerUsageSummary/" + electricPowerUsageSummaryId;
		exportEntry(electricPowerUsageSummaryService.findEntryType(retailCustomerId, usagePointId, electricPowerUsageSummaryId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportElectricPowerUsageSummarys(Long retailCustomerId, Long usagePointId,
						     OutputStream stream, ExportFilter exportFilter) throws IOException {
	    String hrefFragment = "/RetailCustomer/"+ retailCustomerId + "/UsagePoint/" + usagePointId + "/ElectricPowerUsageSummary";
		exportEntries(electricPowerUsageSummaryService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, ElectricPowerUsageSummary.class, hrefFragment);	
	}

    // IntervalBlock

    // - ROOT form
	@Override
	public void exportIntervalBlock(Long intervalBlockId,
					OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/IntervalBlock/" + intervalBlockId;
	    exportEntry(resourceService.findEntryType(intervalBlockId, IntervalBlock.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportIntervalBlocks(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/IntervalBlock";
	    exportEntries(resourceService.findEntryTypeIterator(IntervalBlock.class), stream, exportFilter, IntervalBlock.class, hrefFragment);
	}


    // - XPath form
	@Override
	public void exportIntervalBlock(Long retailCustomerId, Long usagePointId, Long meterReadingId, Long intervalBlockId,
					OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/IntervalBlock/" + intervalBlockId;
		exportEntry(findEntryTypeXPath(retailCustomerId, usagePointId, meterReadingId, intervalBlockId, IntervalBlock.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportIntervalBlocks(Long retailCustomerId, Long usagePointId, Long meterReadingId,
					 OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/IntervalBlock";
		exportEntries(findEntryTypeIteratorXPath(retailCustomerId, usagePointId, meterReadingId, IntervalBlock.class), stream, exportFilter, IntervalBlock.class, hrefFragment);
	}

	// MeterReading
    
    // - ROOT form
	@Override
	public void exportMeterReadings(ServletOutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/MeterReading";
		exportEntries(resourceService.findEntryTypeIterator(MeterReading.class), stream, exportFilter, MeterReading.class, hrefFragment);
	}

	@Override
	public void exportMeterReading(long meterReadingId, ServletOutputStream stream, ExportFilter exportFilter) throws IOException  {
		String hrefFragment = "/MeterReading/" + meterReadingId;
		exportEntry(resourceService.findEntryType(meterReadingId, MeterReading.class), stream, exportFilter, hrefFragment);
	}

    // - XPath
	@Override
	public void exportMeterReading(Long retailCustomerId, Long usagePointId, Long meterReadingId,
				       OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/MeterReading/" + meterReadingId;
		exportEntry(findEntryTypeXPath(retailCustomerId, usagePointId, meterReadingId, 0L, MeterReading.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportMeterReadings(Long retailCustomerId, Long usagePointId,
					OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/MeterReading";
		exportEntries(findEntryTypeIteratorXPath(retailCustomerId, usagePointId, 0L, MeterReading.class), stream, exportFilter, MeterReading.class, hrefFragment);
	}


    // ReadingType

    // ROOT form
	@Override
	public void exportReadingType(Long readingTypeId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ReadingType/" + readingTypeId;
	    exportEntry(resourceService.findEntryType(readingTypeId, ReadingType.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportReadingTypes(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ReadingType";
	    exportEntries(resourceService.findEntryTypeIterator(ReadingType.class), stream, exportFilter, ReadingType.class, hrefFragment);
	}

    // XPath form
	@Override
	public void exportReadingType(Long retailCustomerId, Long usagePointId, Long meterReadingId, Long readingTypeId,
				      OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/MeterReading/" + meterReadingId + "/ReadingType/" + readingTypeId;
		exportEntry(readingTypeService.findEntryType(retailCustomerId, usagePointId, meterReadingId, readingTypeId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportReadingTypes(Long retailCustomerId, Long usagePointId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/ReadingType";
		exportEntries(readingTypeService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, ReadingType.class, hrefFragment);
	}


    // RetailCustomer
    
    // ROOT form
	@Override
	public void exportRetailCustomer(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId;
		exportEntry(findEntryTypeXPath(retailCustomerId, 0L, 0L, 0L, RetailCustomer.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportRetailCustomers(OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		String hrefFragment = "/RetailCustomer";
		exportEntries(findEntryTypeIteratorXPath(0L, 0L, 0L, RetailCustomer.class), stream, exportFilter, UsagePoint.class, hrefFragment);
	}

	
    // Subscription

    // - Root form

	@Override
	public void exportSubscription(Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId;
		exportEntry(resourceService.findEntryType(subscriptionId, Subscription.class), stream, exportFilter, hrefFragment);
		
	}
	
	@Override
	public void exportSubscriptions(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription";
		exportEntries(resourceService.findEntryTypeIterator(Subscription.class), stream, exportFilter, RetailCustomer.class, hrefFragment);
	}

    // - XPath form
	@Override
	public void exportSubscription(Long retailCustomerId, Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/ + retailCustomerId + "+ "/Subscription/" + subscriptionId;
		exportEntry(subscriptionService.findEntryType(retailCustomerId, subscriptionId), stream, exportFilter, hrefFragment);
	}
	
	@Override
	public void exportSubscriptions(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/Subscription";
		exportEntries(subscriptionService.findEntryTypeIterator(retailCustomerId), stream, exportFilter, RetailCustomer.class, hrefFragment);
	}

    // TimeConfiguration

    // - ROOT form
	@Override
	public void exportTimeConfiguration(Long timeConfigurationId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/LocalTimeParameters/" + timeConfigurationId;
	    exportEntry(resourceService.findEntryType(timeConfigurationId, TimeConfiguration.class), stream, exportFilter, hrefFragment);	
	}

	@Override
	public void exportTimeConfigurations(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/LocalTimeParameters";
	    exportEntries(resourceService.findEntryTypeIterator(TimeConfiguration.class), stream, exportFilter, TimeConfiguration.class, hrefFragment);	
	}
	
    // - XPath form
	@Override
	public void exportTimeConfiguration(Long retailCustomerId, Long usagePointId,
			Long timeConfigurationId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
     	String hrefFragment = "/RetalCustomer" + retailCustomerId + "/UsagePoint/" + usagePointId + "/LocalTimeParameters/" + timeConfigurationId;
		exportEntry(timeConfigurationService.findEntryType(retailCustomerId, usagePointId, timeConfigurationId, exportFilter), stream, exportFilter, hrefFragment);	
	}

	@Override
	public void exportTimeConfigurations(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetalCustomer" + retailCustomerId + "/UsagePoint/" + usagePointId + "/LocalTimeParameters";
		exportEntries(timeConfigurationService.findEntryTypeIterator(retailCustomerId, usagePointId, exportFilter), stream, exportFilter, TimeConfiguration.class, hrefFragment);	
	}
	

	// UsagePoints
	//
        // ROOT form
	@Override
	public void exportUsagePoint(Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/UsagePoint/" + usagePointId;
		exportEntry(resourceService.findEntryType(usagePointId, UsagePoint.class), stream, exportFilter, hrefFragment);
		
	}

	@Override
	public void exportUsagePoints(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/UsagePoint";
		exportEntries(resourceService.findEntryTypeIterator(UsagePoint.class), stream, exportFilter, UsagePoint.class, hrefFragment);
	}
	
    // XPath form
	@Override
	public void exportUsagePoint(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId;
		exportEntry(findEntryTypeXPath(retailCustomerId, usagePointId, 0L, 0L, UsagePoint.class), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportUsagePoints(Long retailCustomerId, 
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint";
		exportEntries(findEntryTypeIteratorXPath(retailCustomerId, 0L, 0L, UsagePoint.class), stream, exportFilter, UsagePoint.class, hrefFragment);
	}
	
	// Special forms for Subscription, Bulk, Download
	//
    // -- original Pivotal export function (used in pub/sub flow)
    @Override
    public void exportSubscription(String subscriptionHashedId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionHashedId;
        exportEntriesFull(subscriptionService.findEntriesByHashedId(subscriptionHashedId), stream, exportFilter, hrefFragment);
    }
    
    @Override
    public void exportBatchSubscription(long subscriptionId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Batch/Subscription/" + subscriptionId;
		// first find all the usagePointIds this subscription is related to
		List<Long> usagePointIdList = subscriptionService.findUsagePointIds(subscriptionId);
        exportEntriesFull(resourceService.findEntryTypeIterator(usagePointIdList, UsagePoint.class), stream, exportFilter, hrefFragment);
    }
    
	@Override
	public void exportBatchBulk(long bulkId, OutputStream outputStream, ExportFilter exportFilter) throws IOException {
		// TODO Work with bulkService rather than subscriptionService
		String hrefFragment = "/Batch/Bulk/" + bulkId;
		exportEntriesFull(subscriptionService.findEntryTypeIterator(bulkId), outputStream, exportFilter, hrefFragment);
	}

	
    // export full usagepoint object tree 
    //
	@Override
	public void exportUsagePointsFull(Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Batch/RetailCustomer/" + retailCustomerId;
		exportEntriesFull(usagePointService.findEntryTypeIterator(retailCustomerId), outputStream, exportFilter, hrefFragment);
	}

	@Override
	public void exportUsagePointFull(Long usagePointId, Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Batch/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId;
		exportEntriesFull(usagePointService.findEntryTypeIterator(retailCustomerId, usagePointId), outputStream, exportFilter, hrefFragment);
		
	}

    // worker functions
    //
	
	private void buildHeader (OutputStream stream, String hrefFragment) throws IOException {
	  
		String selfRef = "<link href=\"" + applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment + "\" rel=\"self\"/>";
    	DateTimeType updated = DateConverter.toDateTimeType(new Date());
        String temp = updated.getValue().toXMLFormat();
    	String uuid = UUID.randomUUID().toString();

        stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
        stream.write("<?xml-stylesheet type=\"text/xsl\" href=\"/GreenButtonDataStyleSheet.xslt\"?>".getBytes());
        stream.write("<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n".getBytes());
        stream.write("<id>urn:uuid:".getBytes());
        stream.write(uuid.getBytes());
        stream.write("</id>\n".getBytes());
        stream.write("<title>Green Button Usage Feed</title>\n".getBytes());
        stream.write("<updated>".getBytes());
        stream.write(temp.getBytes());
        stream.write("</updated>\n".getBytes());
        stream.write(selfRef.getBytes());
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	//TODO: this needs to be templated and RetailCustomer inherited from IdentifiedObject to remove the above supress warnings
	private void exportEntries(EntryTypeIterator entries, OutputStream stream,
			ExportFilter exportFilter, Class resourceClass, String hrefFragment)
			throws IOException {

		buildHeader(stream, hrefFragment);
		// hrefFragment = adjustFragment(hrefFragment);
		if (entries != null) {

			while (entries.hasNext()) {
				try {
					EntryType entry = entries.nextEntry(resourceClass);
					// export the Entry and put the right tail on the URI
					exportEntry(entry, stream, exportFilter, hrefFragment + "/" + entry.getContent().getContentId(resourceClass));
				} catch (Exception e) {
					stream.write("/* The requested collection contains no resources */"
							.getBytes());
					stream.write("</feed>".getBytes());
				}
			}
		}
		stream.write("</feed>".getBytes());
	}
    
	private String adjustFragment(String fragment, EntryType entry ) {
		// TODO there may be other setup things - Likely BatchList
		// if that still exists.
		String result = fragment;
		if (fragment.contains("DownloadMyData")) {
			result.replace("DownloadMyData", "UsagePoint");
		}
		if (fragment.contains("Batch")) {
			if (fragment.contains("Bulk")) {
				// ToDo need the proper URI fragment for a Bulk
				UsagePoint up = entry.getContent().getUsagePoint();
				RetailCustomer rc = up.getRetailCustomer();
				// TODO here need the proper URI fragment for a subscription
				result = "/RetailCustomer/" + rc.getId() + "/UsagePoint";			}
			if (fragment.contains("Subscription")) {
				// the entry contains a valid usage point at this stage
				// 
				UsagePoint up = entry.getContent().getUsagePoint();
				RetailCustomer rc = up.getRetailCustomer();

				result = "/RetailCustomer/" + rc.getId() + "/UsagePoint";
			}
			if (fragment.contains("/Batch/RetailCustomer")) {
                result = fragment.replace("/Batch",  "");
                if (!(fragment.contains("/UsagePoint"))) {
                	result = result + "/UsagePoint";
                }
			}
		}
		return result;
	}
    // to export a single entry (w/o the <feed>...</feed> wrappers
    
	private void exportEntry(EntryType entry, OutputStream stream,
			ExportFilter exportFilter, String hrefFragment) throws IOException {

        AtomMarshallerListener uriListener = new AtomMarshallerListener(applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment);
        uriListener.setRelList(entry.getContent().buildRelHref(applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment));
        fragmentMarshaller.setMarshallerListener(uriListener);
        
		StreamResult result = new StreamResult(stream);
        try {
		if (exportFilter.matches(entry)) {
			fragmentMarshaller.marshal(entry, result);
		}
        } catch (Exception e) {
        	throw(e);
        }
	}

	private void exportEntriesFull(EntryTypeIterator entries,
			OutputStream stream, ExportFilter exportFilter, String hrefFragment)
			throws IOException {

		// construct the <feed> header components
		//
		buildHeader(stream, hrefFragment);

		if (entries != null) {

			while (entries.hasNext()) {
				try {
					EntryType entry = entries.next();
					// last minute fixup b/f doing the full dump
					// changing self of the feed to self of the root resource
					//
					if (entry.getContent().getUsagePoint() != null) {
					      hrefFragment = adjustFragment(hrefFragment, entry);
					}
					exportEntryFull(entry, stream, exportFilter, hrefFragment);
				} catch (Exception e) {
					stream.write("/* The requested collection contains no resources */"
							.getBytes());
					stream.write("</feed>".getBytes());
				}

			}
		}
		stream.write("</feed>".getBytes());
	}
    
    // to export a single entry (w/o the <feed>...</feed> wrappers
    
	private void exportEntryFull(EntryType entry, OutputStream stream,
			ExportFilter exportFilter, String hrefFragment) throws IOException {
         
    	// setup a listener so that the adapters may later be fed the fragment;
    	//
        AtomMarshallerListener uriListener = new AtomMarshallerListener(applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment);
        
        uriListener.setRelList(entry.getContent().buildRelHref(applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment));
        fragmentMarshaller.setMarshallerListener(uriListener);
        
		StreamResult result = new StreamResult(stream);
        try {
		if (exportFilter.matches(entry)) {
			fragmentMarshaller.marshal(entry, result);
		}
        } catch (Exception e) {
        	throw(e);
        }
	}
	
	@SuppressWarnings("unchecked")
	// TODO: need to make RetailCustomer inherit from IdentifiedObject to remove the above
	private EntryType findEntryTypeXPath(Long id1, Long id2, Long id3, Long id4, @SuppressWarnings("rawtypes") Class clazz) {
		EntryType result = null;
		List<Long> temp = new ArrayList<Long>();
		try {
				
			if (id4 != 0) {
				temp.add(resourceService.findIdByXPath(id1, id2, id3, id4, clazz));
			} else {
				if (id3 != 0) {
					temp.add(resourceService.findIdByXPath(id1, id2, id3, clazz));
				} else {
					if (id2 != 0) {
					   temp.add(resourceService.findIdByXPath(id1, id2, clazz));
				    } else {
				       temp.add(resourceService.findIdByXPath(id1, clazz));
			        }
				}
			}
			result = null;
		} catch (Exception e) {
			System.out.printf("**** Error in Query: %s\n", e.toString());
			result = null;
		}
		if (temp != null) {
			result = (new EntryTypeIterator(resourceService, temp, clazz)).nextEntry(clazz);
		}
		return result;		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	// TODO: need to make RetailCustomer inherit from IdentifiedObject to remove the above @Suppress
	private EntryTypeIterator findEntryTypeIteratorXPath(Long id1, Long id2, Long id3, 
			Class clazz) {
		EntryTypeIterator result = null;
		List<Long> temp = new ArrayList<Long>();
		try {
			if (id3 != 0) {
				temp = resourceService.findAllIdsByXPath(id1, id2, id3, clazz);
			} else {
				if (id2 != 0) {
					temp = resourceService.findAllIdsByXPath(id1, id2, clazz);
				} else {
					temp = resourceService.findAllIdsByXPath(id1, clazz);
				}
			}
			result = (new EntryTypeIterator(resourceService, temp, clazz));
		} catch (Exception e) {
			System.out.printf("**** Error in Query: %s\n", e.toString());
			result = null;
		}
		return result;		
	}

}
