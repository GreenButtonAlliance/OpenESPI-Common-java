package org.energyos.espi.common.service;

import org.energyos.espi.common.utils.ExportFilter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public interface ExportService {
    // ApplicationInformation

    //  - ROOT form
    public void exportApplicationInformation(Long applicationInformationId, OutputStream stream, ExportFilter exportFilter) throws IOException;

    public void exportApplicationInformations(OutputStream stream, ExportFilter exportFilter) throws IOException;

    // Authorization
    //  - ROOT form
	public void exportAuthorization(Long authorizationId, OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportAuthorizations(OutputStream stream, ExportFilter exportFilter) throws IOException;

    //  - XPath form
	public void exportAuthorization(Long retailCustomerId, Long authorizationId, OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportAuthorizations(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws IOException;


    // ElectricPowerQualitySummary
    //  - ROOT form

	void exportElectricPowerQualitySummary(Long electricPowerQualitySummaryId, OutputStream outputStream, ExportFilter exportFilter) throws IOException;

	void exportElectricPowerQualitySummarys(OutputStream outputStream, ExportFilter exportFilter) throws IOException;

    //  - XPath form
	public void exportElectricPowerQualitySummary(Long retailCustomerId, Long usagePointId, Long electricPowerQualitySummaryId,
						      OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportElectricPowerQualitySummarys(Long retailCustomerId, Long usagePointId,
						       OutputStream stream, ExportFilter exportFilter) throws IOException;


    // ElectricPowerUsageSummary
    //  - ROOT form

	void exportElectricPowerUsageSummarys(ServletOutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	void exportElectricPowerUsageSummary(long electricPowerUsageSummaryId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException;

    //  - XPath form

	public void exportElectricPowerUsageSummary(Long retailCustomerId, Long usagePointId, Long electricPowerUsageSummaryId,
						    OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportElectricPowerUsageSummarys(Long retailCustomerId, Long usagePointId,
						     OutputStream stream, ExportFilter exportFilter) throws IOException;


    // IntervalBlock
    //  - ROOT form
	void exportIntervalBlocks(OutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	void exportIntervalBlock(Long intervalBlockId,
			OutputStream outputStream, ExportFilter exportFilter) throws IOException;

    //  - XPath form

	public void exportIntervalBlock(Long retailCustomerId, Long usagePointId, Long meterReadingId, Long intervalBlockId,
					OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportIntervalBlocks(Long retailCustomerId, Long usagePointId, Long meterReadingId,
					 OutputStream stream, ExportFilter exportFilter) throws IOException;

	// MeterReading
    //  - ROOT form
	void exportMeterReadings(ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException ;

	void exportMeterReading(long meterReadingId, ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException ;

    //  - XPath form
	public void exportMeterReading(Long retailCustomerId, Long usagePointId, Long meterReadingId,
				       OutputStream stream, ExportFilter exportFilter) throws IOException;
	
	public void exportMeterReadings(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	// ReadingType
	// - ROOT form
	void exportReadingTypes(OutputStream outputStream, ExportFilter exportFilter) throws IOException;

	void exportReadingType(Long readingTypeId, OutputStream outputStream, ExportFilter exportFilter) throws IOException;

	// - XPath form
	public void exportReadingType(Long retailCustomerId, Long usagePointId,	Long meterTypeId, Long readingTypeId,
				      OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportReadingTypes(Long retailCustomerId, Long usagePointId,
				       OutputStream stream, ExportFilter exportFilter) throws IOException;

    // RetailCustomer
    //  - ROOT form

	public void exportRetailCustomer(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportRetailCustomers(OutputStream stream, ExportFilter exportFilter) throws IOException;


	// Subscriptions
	//
       // ROOT forms
       // -- TODO: original Pivotal version - used for pub/sub activity b/c of the String/Hashed implication 
       public void exportSubscription(String subscriptionHashedId, OutputStream stream, ExportFilter exportResourceFilter) throws IOException;
	
       public void exportSubscription(Long subscriptionId, OutputStream stream, ExportFilter exportResourceFilter) throws IOException;

       public void exportSubscriptions(OutputStream stream, ExportFilter exportResourceFilter) throws IOException;

        // XPath forms

	public void exportSubscriptions(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportSubscription(Long retailCustomerId, Long subscriptionId, OutputStream stream, ExportFilter exportFilter) throws IOException;

    // TimeConfiguration
    //  - ROOT form

	public void exportTimeConfiguration(Long timeConfigurationId, OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportTimeConfigurations(OutputStream stream, ExportFilter exportFilter) throws IOException;

    //  - XPath form

	public void exportTimeConfiguration(Long retailCustomerId, Long usagePointId, Long timeConfigurationId,
				     OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportTimeConfigurations(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;
	
	// UsagePoints
	// - ROOT form
	void exportUsagePoint(Long usagePointId, OutputStream stream, ExportFilter exportResourceFilter) throws IOException;

	void exportUsagePoints(OutputStream stream, ExportFilter exportResourceFilter) throws IOException;
        
	// - XPath form
	void exportUsagePoint(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportResourceFilter) throws IOException;

	void exportUsagePoints(Long retailCustomerId, OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;

	// - Full Export form
	void exportUsagePointsFull(Long retailCustomerId, ServletOutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	void exportUsagePointFull(Long usagePointId, Long RetailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter)  throws IOException;

}
