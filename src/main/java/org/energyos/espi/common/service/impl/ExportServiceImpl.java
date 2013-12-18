package org.energyos.espi.common.service.impl;


import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.service.*;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamResult;

import java.io.IOException;
import java.io.OutputStream;

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
    
    @Override
    public void exportSubscription(String subscriptionHashedId, OutputStream stream, ExportFilter exportFilter) throws IOException {
        exportEntries(subscriptionService.findEntriesByHashedId(subscriptionHashedId), stream, exportFilter);
    }

	@Override
	public void exportSubscriptions(OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exportSubscription(Long retailCustomerId, Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(subscriptionService.find(retailCustomerId, subscriptionId), stream, exportFilter);
		
	}
	
	@Override
	public void exportSubscriptions(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void exportApplicationInformation(Long applicationInformationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(applicationInformationService.find(applicationInformationId), stream, exportFilter);
	}

	@Override
	public void exportApplicationInformations(OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntries(applicationInformationService.find(), stream, exportFilter);		

	}

	@Override
	public void exportAuthorization(Long retailCustomerId, Long authorizationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(authorizationService.find(retailCustomerId, authorizationId), stream, exportFilter);			
	}


	@Override
	public void exportAuthorizations(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntries(authorizationService.find(retailCustomerId), stream, exportFilter);		
	}

	@Override
	public void exportElectricPowerQualitySummary(Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(electricPowerQualitySummaryService.find(retailCustomerId, usagePointId, electricPowerQualitySummaryId), stream, exportFilter);
	}

	@Override
	public void exportElectricPowerQualitySummarys(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(electricPowerQualitySummaryService.find(retailCustomerId, usagePointId), stream, exportFilter);	
	}


	@Override
	public void exportElectricPowerUsageSummary(Long retailCustomerId, Long usagePointId,
			Long electricPowerUsageSummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(electricPowerUsageSummaryService.find(retailCustomerId, usagePointId, electricPowerUsageSummaryId), stream, exportFilter);
	}

	@Override
	public void exportElectricPowerUsageSummarys(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(electricPowerUsageSummaryService.find(retailCustomerId, usagePointId), stream, exportFilter);	
	}

	@Override
	public void exportIntervalBlock(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(intervalBlockService.find(retailCustomerId, usagePointId, meterReadingId, intervalBlockId), stream, exportFilter);
		
	}

	@Override
	public void exportIntervalBlocks(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntries(intervalBlockService.find(retailCustomerId, usagePointId, meterReadingId), stream, exportFilter);
		
	}


	@Override
	public void exportMeterReading(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntry(meterReadingService.find(retailCustomerId, usagePointId, meterReadingId), stream, exportFilter);
	}

	@Override
	public void exportMeterReadings(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(meterReadingService.find(retailCustomerId, usagePointId), stream, exportFilter);
	}


	@Override
	public void exportReadingType(Long retailCustomerId, Long usagePointId,
			Long readingTypeId, OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntry(meterReadingService.find(retailCustomerId, usagePointId, readingTypeId), stream, exportFilter);
	}

	@Override
	public void exportReadingTypes(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(readingTypeService.find(retailCustomerId, usagePointId), stream, exportFilter);
	}


	@Override
	public void exportRetailCustomer(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(retailCustomerService.find(retailCustomerId), stream, exportFilter);
	}

	@Override
	public void exportRetailCustomers(OutputStream stream, ExportFilter exportFilter)
			throws IOException {
		exportEntries(retailCustomerService.find(), stream, exportFilter);
	}



	@Override
	public void exportTimeConfiguration(Long retailCustomerId, Long usagePointId,
			Long timeConfigurationId, OutputStream stream,
			ExportFilter exportFilter) throws IOException {
		exportEntry(timeConfigurationService.find(retailCustomerId, usagePointId, timeConfigurationId, exportFilter), stream, exportFilter);	
	}

	@Override
	public void exportTimeConfigurations(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(timeConfigurationService.find(retailCustomerId, usagePointId, exportFilter), stream, exportFilter);	
	}
	
	// UsagePoints
	//
	@Override
	public void exportUsagePoint(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(usagePointService.find(retailCustomerId, usagePointId, exportFilter), stream, exportFilter);
		
	}

	@Override
	public void exportUsagePoints(Long retailCustomerId, 
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(usagePointService.find(retailCustomerId), stream, exportFilter);
	}
	
	@Override
	public void exportUsagePoint(Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntry(usagePointService.find(usagePointId, exportFilter), stream, exportFilter);
		
	}

	@Override
	public void exportUsagePoints(OutputStream stream, ExportFilter exportFilter) throws IOException {
		exportEntries(usagePointService.find(exportFilter), stream, exportFilter);
	}
	
    // worker functions
    //
    private void exportEntries(EntryTypeIterator entries, OutputStream stream, ExportFilter exportFilter) throws IOException {

        stream.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n".getBytes());
        stream.write("<feed xmlns=\"http://www.w3.org/2005/Atom\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">".getBytes());
        while (entries.hasNext()) {
        	try {
        		EntryType entry = entries.next();
            	exportEntry(entry, stream, exportFilter);        		
        	} catch (Exception e) {
        	  stream.write("/* TODO - Remove this message - let the error happenan error happened in exportEntry - it is caused by an assumption of required (TimeConfiguration) relationships */".getBytes());	
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
        	stream.write("/* an error happened in exportEntry */".getBytes());
        }
	}

}
