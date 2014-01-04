package org.energyos.espi.common.service.impl;


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
import org.energyos.espi.common.service.*;
import org.energyos.espi.common.utils.DateConverter;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.energyos.espi.common.utils.AtomMarshallerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.UUID;

@Service
public class ExportServiceImpl implements ExportService {

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
		exportEntry(applicationInformationService.findEntryType(applicationInformationId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportApplicationInformations(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ApplicationInformation";
		exportEntries(applicationInformationService.findEntryTypeIterator(), stream, exportFilter, ApplicationInformation.class, hrefFragment);		
	}

    // Authorization

    // - ROOT form
	@Override
	public void exportAuthorization(Long authorizationId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Authorization/" + authorizationId;
		exportEntry(authorizationService.findEntryType(0L, authorizationId), stream, exportFilter, hrefFragment);			
	}

	@Override
	public void exportAuthorizations(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Authorization";
		exportEntries(authorizationService.findEntryTypeIterator(), stream, exportFilter, Authorization.class, hrefFragment);		
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
		exportEntry(electricPowerQualitySummaryService.findEntryType(0L, 0L, electricPowerQualitySummaryId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportElectricPowerQualitySummarys(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/ElectricPowerQualitySummary";
	    exportEntries(electricPowerQualitySummaryService.findEntryTypeIterator(0L, 0L), stream, exportFilter, ElectricPowerQualitySummary.class, hrefFragment);	
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
		exportEntries(electricPowerUsageSummaryService.findEntryTypeIterator(0L, 0L), outputStream, exportFilter, ElectricPowerQualitySummary.class, hrefFragment);	
	}

	@Override
	public void exportElectricPowerUsageSummary(long electricPowerUsageSummaryId, ServletOutputStream outputStream,	ExportFilter exportFilter) throws IOException {
	    String hrefFragment = "/ElectricPowerUsageSummary/" + electricPowerUsageSummaryId;
		exportEntry(electricPowerUsageSummaryService.findEntryType(0L, 0L, electricPowerUsageSummaryId), outputStream, exportFilter, hrefFragment);
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
	    exportEntry(intervalBlockService.findEntryType(0L, 0L, 0L, intervalBlockId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportIntervalBlocks(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/IntervalBlock";
	    exportEntries(intervalBlockService.findEntryTypeIterator(0L, 0L, 0L), stream, exportFilter, IntervalBlock.class, hrefFragment);
	}


    // - XPath form
	@Override
	public void exportIntervalBlock(Long retailCustomerId, Long usagePointId, Long meterReadingId, Long intervalBlockId,
					OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/IntervalBlock/" + intervalBlockId;
		exportEntry(intervalBlockService.findEntryType(retailCustomerId, usagePointId, meterReadingId, intervalBlockId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportIntervalBlocks(Long retailCustomerId, Long usagePointId, Long meterReadingId,
					 OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/IntervalBlock";
		exportEntries(intervalBlockService.findEntryTypeIterator(retailCustomerId, usagePointId, meterReadingId), stream, exportFilter, IntervalBlock.class, hrefFragment);
	}

	// MeterReading
    
    // - ROOT form
	@Override
	public void exportMeterReadings(ServletOutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/MeterReading";
		exportEntries(meterReadingService.findEntryTypeIterator(), stream, exportFilter, MeterReading.class, hrefFragment);
	}

	@Override
	public void exportMeterReading(long meterReadingId, ServletOutputStream stream, ExportFilter exportFilter) throws IOException  {
		String hrefFragment = "/MeterReading/" + meterReadingId;
		exportEntry(meterReadingService.findEntryType(meterReadingId), stream, exportFilter, hrefFragment);
	}

    // - XPath
	@Override
	public void exportMeterReading(Long retailCustomerId, Long usagePointId, Long meterReadingId,
				       OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/MeterReading/" + meterReadingId;
		exportEntry(meterReadingService.findEntryType(retailCustomerId, usagePointId, meterReadingId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportMeterReadings(Long retailCustomerId, Long usagePointId,
					OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/MeterReading";
		exportEntries(meterReadingService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, MeterReading.class, hrefFragment);
	}


    // ReadingType

    // ROOT form
	@Override
	public void exportReadingType(Long readingTypeId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Readingtype/" + readingTypeId;
	    exportEntry(readingTypeService.findEntryType(0L, 0L, 0L, readingTypeId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportReadingTypes(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Readingtype";
	    exportEntries(readingTypeService.findEntryTypeIterator(0L, 0L), stream, exportFilter, ReadingType.class, hrefFragment);
	}

    // XPath form
	@Override
	public void exportReadingType(Long retailCustomerId, Long usagePointId, Long meterReadingId, Long readingTypeId,
				      OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/MeterReading/" + meterReadingId + "/Readingtype/" + readingTypeId;
		exportEntry(readingTypeService.findEntryType(retailCustomerId, usagePointId, meterReadingId, readingTypeId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportReadingTypes(Long retailCustomerId, Long usagePointId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId + "/Readingtype";
		exportEntries(readingTypeService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, ReadingType.class, hrefFragment);
	}


    // RetailCustomer
    
    // ROOT form
	@Override
	public void exportRetailCustomer(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId;
		exportEntry(retailCustomerService.findEntryType(retailCustomerId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportRetailCustomers(OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		String hrefFragment = "/RetailCustomer";
		exportEntries(retailCustomerService.findEntryTypeIterator(), stream, exportFilter, RetailCustomer.class, hrefFragment);
	}

    // Subscription

    // - Root form

    // -- original Pivotal export function (used in pub/sub flow)
    @Override
    public void exportSubscription(String subscriptionHashedId, OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription" + subscriptionHashedId;
        exportEntriesFull(subscriptionService.findEntriesByHashedId(subscriptionHashedId), stream, exportFilter, hrefFragment);
    }

	@Override
	public void exportSubscription(Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription/" + subscriptionId;
		exportEntry(subscriptionService.findEntryType(0L, subscriptionId), stream, exportFilter, hrefFragment);
		
	}
	
	@Override
	public void exportSubscriptions(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Subscription";
		exportEntries(subscriptionService.findEntryTypeIterator(0L), stream, exportFilter, RetailCustomer.class, hrefFragment);
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
		String hrefFragment = "/TimeConfiguratoin/" + timeConfigurationId;
	    exportEntry(timeConfigurationService.findEntryType(0L, 0L, timeConfigurationId, exportFilter), stream, exportFilter, hrefFragment);	
	}

	@Override
	public void exportTimeConfigurations(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/TimeConfiguration";
	    exportEntries(timeConfigurationService.findEntryTypeIterator(0L, 0L, exportFilter), stream, exportFilter, TimeConfiguration.class, hrefFragment);	
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
		exportEntry(usagePointService.findEntryType(usagePointId), stream, exportFilter, hrefFragment);
		
	}

	@Override
	public void exportUsagePoints(OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/UsagePoint";
		exportEntries(usagePointService.findEntryTypeIterator(), stream, exportFilter, UsagePoint.class, hrefFragment);
	}
	
    // XPath form
	@Override
	public void exportUsagePoint(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint/" + usagePointId;
		exportEntry(usagePointService.findEntryType(retailCustomerId, usagePointId), stream, exportFilter, hrefFragment);
	}

	@Override
	public void exportUsagePoints(Long retailCustomerId, 
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/RetailCustomer/" + retailCustomerId + "/UsagePoint";
		exportEntries(usagePointService.findEntryTypeIterator(retailCustomerId), stream, exportFilter, UsagePoint.class, hrefFragment);
	}
	
    // export full usagepoint object tree 
    //
	@Override
	public void exportUsagePointsFull(Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Readingtype";
		exportEntriesFull(usagePointService.findEntryTypeIterator(retailCustomerId), outputStream, exportFilter, hrefFragment);
	}

	@Override
	public void exportUsagePointFull(Long usagePointId, Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException {
		String hrefFragment = "/Readingtype";
		exportEntriesFull(usagePointService.findEntryTypeIterator(retailCustomerId, usagePointId), outputStream, exportFilter, hrefFragment);
		
	}

    // worker functions
    //
	
	private void buildHeader (OutputStream stream, String hrefFragment) throws IOException {
	  
		String selfRef = "<link ref=\"self\" href=\"" + applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment + "\"/>";
    	DateTimeType updated = DateConverter.toDateTimeType(new Date());
        String temp = updated.getValue().toXMLFormat();
    	String uuid = UUID.randomUUID().toString();

        stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
        stream.write("<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">".getBytes());
        stream.write("<id>urn:uuid:".getBytes());
        stream.write(uuid.getBytes());
        stream.write("</id>\n".getBytes());
        stream.write("<title>Green Button Usage Feed</title>\n".getBytes());
        stream.write("<updated>".getBytes());
        stream.write(temp.getBytes());
        stream.write("</updated>\n".getBytes());
        stream.write(selfRef.getBytes());
	}
	
	private void exportEntries(EntryTypeIterator entries, OutputStream stream,
			ExportFilter exportFilter, Class resourceClass, String hrefFragment)
			throws IOException {

		buildHeader(stream, hrefFragment);

		if (entries != null) {

			while (entries.hasNext()) {
				try {
					EntryType entry = entries.nextEntry(resourceClass);
					exportEntry(entry, stream, exportFilter, hrefFragment);
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
    
	private void exportEntry(EntryType entry, OutputStream stream,
			ExportFilter exportFilter, String hrefFragment) throws IOException {

        AtomMarshallerListener uriListener = new AtomMarshallerListener(applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment);
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

    private void exportEntriesFull(EntryTypeIterator entries, OutputStream stream, ExportFilter exportFilter, String hrefFragment) throws IOException {
        
        // construct the <feed> header components
        //
    	buildHeader(stream, hrefFragment);

        if (entries != null) {
        
        while (entries.hasNext()) {
        	try {
        		EntryType entry = entries.next();
            	exportEntryFull(entry, stream, exportFilter, hrefFragment);        		
        	} catch (Exception e) {
        	  stream.write("/* The requested collection contains no resources */".getBytes());	
              stream.write("</feed>".getBytes());
        	}
 
          }
        }
        stream.write("</feed>".getBytes());
    }
    
    // to export a single entry (w/o the <feed>...</feed> wrappers
    
	private void exportEntryFull(EntryType entry, OutputStream stream,
			ExportFilter exportFilter, String hrefFragment) throws IOException {
         
    	// setup a listener so that the adapters may later be fed the href fragment;
    	//
        AtomMarshallerListener uriListener = new AtomMarshallerListener(applicationInformationService.getDataCustodianResourceEndpoint() + hrefFragment);
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

}
