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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.servlet.ServletOutputStream;
import javax.xml.bind.JAXBException;
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
	@Override
	public void exportApplicationInformation(Long applicationInformationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(applicationInformationService.findEntryType(applicationInformationId), stream, exportFilter);
	}

	@Override
	public void exportApplicationInformations(OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntries(applicationInformationService.findEntryTypeIterator(), stream, exportFilter, ApplicationInformation.class);		

	}

	@Override
	public void exportAuthorization(Long retailCustomerId, Long authorizationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(authorizationService.findEntryType(retailCustomerId, authorizationId), stream, exportFilter);			
	}


	@Override
	public void exportAuthorizations(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntries(authorizationService.findEntryTypeIterator(retailCustomerId), stream, exportFilter, Authorization.class);		
	}

	@Override
	public void exportElectricPowerQualitySummary(Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(electricPowerQualitySummaryService.findEntryType(retailCustomerId, usagePointId, electricPowerQualitySummaryId), stream, exportFilter);
	}

	@Override
	public void exportElectricPowerQualitySummarys(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(electricPowerQualitySummaryService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, ElectricPowerQualitySummary.class);	
	}


	@Override
	public void exportElectricPowerUsageSummary(Long retailCustomerId, Long usagePointId,
			Long electricPowerUsageSummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(electricPowerUsageSummaryService.findEntryType(retailCustomerId, usagePointId, electricPowerUsageSummaryId), stream, exportFilter);
	}

	@Override
	public void exportElectricPowerUsageSummarys(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(electricPowerUsageSummaryService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, ElectricPowerUsageSummary.class);	
	}

	@Override
	public void exportIntervalBlock(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(intervalBlockService.findEntryType(retailCustomerId, usagePointId, meterReadingId, intervalBlockId), stream, exportFilter);
		
	}

	@Override
	public void exportIntervalBlocks(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntries(intervalBlockService.findEntryTypeIterator(retailCustomerId, usagePointId, meterReadingId), stream, exportFilter, IntervalBlock.class);
		
	}

	// MeterReading

	@Override
	public void exportMeterReadings(ServletOutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntries(meterReadingService.findEntryTypeIterator(), stream, exportFilter, MeterReading.class);
		
	}

	@Override
	public void exportMeterReading(long meterReadingId, ServletOutputStream stream, ExportFilter exportFilter) throws IOException  {
		exportEntry(meterReadingService.findEntryType(meterReadingId), stream, exportFilter);
		
	}

	@Override
	public void exportMeterReading(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntry(meterReadingService.findEntryType(retailCustomerId, usagePointId, meterReadingId), stream, exportFilter);
	}

	@Override
	public void exportMeterReadings(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(meterReadingService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, MeterReading.class);
	}


	@Override
	public void exportReadingType(Long retailCustomerId, Long usagePointId, Long meterReadingId,
			Long readingTypeId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntry(readingTypeService.findEntryType(retailCustomerId, usagePointId, meterReadingId, readingTypeId), stream, exportFilter);
	}

	@Override
	public void exportReadingTypes(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(readingTypeService.findEntryTypeIterator(retailCustomerId, usagePointId), stream, exportFilter, ReadingType.class);
	}


	@Override
	public void exportRetailCustomer(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(retailCustomerService.findEntryType(retailCustomerId), stream, exportFilter);
	}

	@Override
	public void exportRetailCustomers(OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntries(retailCustomerService.findEntryTypeIterator(), stream, exportFilter, RetailCustomer.class);
	}

    @Override
    public void exportSubscription(String subscriptionHashedId, OutputStream stream, ExportFilter exportFilter) throws IOException {
        exportEntriesFull(subscriptionService.findEntriesByHashedId(subscriptionHashedId), stream, exportFilter);
    }

	@Override
	public void exportSubscriptions(OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportSubscription(Long retailCustomerId, Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(subscriptionService.findEntryType(retailCustomerId, subscriptionId), stream, exportFilter);
		
	}
	
	@Override
	public void exportSubscriptions(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportTimeConfiguration(Long retailCustomerId, Long usagePointId,
			Long timeConfigurationId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(timeConfigurationService.findEntryType(retailCustomerId, usagePointId, timeConfigurationId, exportFilter), stream, exportFilter);	
	}

	@Override
	public void exportTimeConfigurations(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(timeConfigurationService.findEntryTypeIterator(retailCustomerId, usagePointId, exportFilter), stream, exportFilter, TimeConfiguration.class);	
	}
	
	// UsagePoints
	//
	@Override
	public void exportUsagePoint(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(usagePointService.findEntryType(retailCustomerId, usagePointId), stream, exportFilter);
		
	}

	@Override
	public void exportUsagePoints(Long retailCustomerId, 
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(usagePointService.findEntryTypeIterator(retailCustomerId), stream, exportFilter, UsagePoint.class);
	}
	
	@Override
	public void exportUsagePoint(Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(usagePointService.findEntryType(usagePointId), stream, exportFilter);
		
	}

	@Override
	public void exportUsagePoints(OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(usagePointService.findEntryTypeIterator(), stream, exportFilter, UsagePoint.class);
	}
	
    // worker functions
    //
    private void exportEntries(EntryTypeIterator entries, OutputStream stream, ExportFilter exportFilter, Class resourceClass) throws IOException {
    	DateTimeType updated = DateConverter.toDateTimeType(new Date());
        String temp = updated.toString();
    	String uuid = UUID.randomUUID().toString();
        String selfRef = "<link rel=\"self\" href=\"";
        selfRef += "/espi/1_1/resource/xxxxTODOxxxx";
        selfRef += "\"</link>\n";
    	//GregorianCalendar updated = new GregorianCalendar();
    	//updated.setTimeZone(TimeZone.getTimeZone("UTC"));
    	//String temp = DateConverter.epoch().toString();
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

        if (entries != null) {
        
        while (entries.hasNext()) {
        	try {
        		EntryType entry = entries.nextEntry(resourceClass);
            	exportEntry(entry, stream, exportFilter);        		
        	} catch (Exception e) {
        	  stream.write("/* The requested collection contains no resources */".getBytes());	
              stream.write("</feed>".getBytes());
        	}
 
          }
        }
        stream.write("</feed>".getBytes());
    }
    
    // to export a single entry (w/o the <feed>...</feed> wrappers
    
	private void exportEntry(EntryType entry, OutputStream stream,
			ExportFilter exportFilter) throws IOException {

		StreamResult result = new StreamResult(stream);
        try {
		if (exportFilter.matches(entry)) {
			fragmentMarshaller.marshal(entry, result);
		}
        } catch (Exception e) {
        	throw(e);
        }
	}

    private void exportEntriesFull(EntryTypeIterator entries, OutputStream stream, ExportFilter exportFilter) throws IOException {
    	DateTimeType updated = DateConverter.toDateTimeType(new Date());
        String temp = updated.toString();
    	String uuid = UUID.randomUUID().toString();
        String selfRef = "<link rel=\"self\" href=\"";
        selfRef += "/espi/1_1/resource/xxxTODOxxx/";
        selfRef += "\"/>\n";
    	//GregorianCalendar updated = new GregorianCalendar();
    	//updated.setTimeZone(TimeZone.getTimeZone("UTC"));
    	//String temp = DateConverter.epoch().toString();
        stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
        stream.write("<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">\n".getBytes());
        stream.write("<id>urn:uuid:".getBytes());
        stream.write(uuid.getBytes());
        stream.write("</id>\n".getBytes());
        stream.write("<title>Green Button Collection</title>\n".getBytes());
        stream.write("<updated>".getBytes());
        stream.write(temp.getBytes());
        stream.write("</updated>\n".getBytes());
        stream.write(selfRef.getBytes());

        if (entries != null) {
        
        while (entries.hasNext()) {
        	try {
        		EntryType entry = entries.next();
            	exportEntryFull(entry, stream, exportFilter);        		
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
			ExportFilter exportFilter) throws IOException {

		StreamResult result = new StreamResult(stream);
        try {
		if (exportFilter.matches(entry)) {
			fragmentMarshaller.marshal(entry, result);
		}
        } catch (Exception e) {
        	throw(e);
        }
	}

	@Override
	public void exportApplicationInformations(Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportApplicationInformation(Long retailCustomerId,
			long authorizationId, ServletOutputStream outputStream,
			ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportElectricPowerQualitySummarys(
			ServletOutputStream outputStream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportElectricPowerQualitySummary(
			long electricPowerQualitySummaryId,
			ServletOutputStream outputStream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportElectricPowerUsageSummarys(
			ServletOutputStream outputStream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportElectricPowerUsageSummary(
			long electricPowerUsageSummaryId, ServletOutputStream outputStream,
			ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportIntervalBlocks(ServletOutputStream outputStream,
			ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportIntervalBlock(long intervalBlockId,
			ServletOutputStream outputStream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportReadingTypes(ServletOutputStream outputStream,
			ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportReadingType(long readingTypeId,
			ServletOutputStream outputStream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportTimeConfigurations(ServletOutputStream outputStream,
			ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportTimeConfiguration(long timeConfigurationId,
			ServletOutputStream outputStream, ExportFilter exportFilter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportUsagePointsFull(Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException {
		exportEntriesFull(usagePointService.findEntryTypeIterator(retailCustomerId), outputStream, exportFilter);
		
	}

	@Override
	public void exportUsagePointFull(Long usagePointId, Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException {
		exportEntriesFull(usagePointService.findEntryTypeIterator(retailCustomerId, usagePointId), outputStream, exportFilter);
		
	}
	
}
