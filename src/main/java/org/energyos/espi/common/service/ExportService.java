package org.energyos.espi.common.service;

import org.energyos.espi.common.utils.ExportFilter;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;

public interface ExportService {
	void exportSubscription(String subscriptionHashedId, OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;
	
    void exportSubscriptions(OutputStream stream,
	        ExportFilter exportResourceFilter) throws IOException;

	public void exportSubscription(Long retailCustomerId, Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;
	
	void exportSubscriptions(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

    public void exportApplicationInformation(Long applicationInformationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportApplicationInformations(OutputStream stream, ExportFilter exportFilter)
			throws IOException;

	public void exportAuthorization(Long retailCustomerId, Long authorizationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportAuthorizations(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	public void exportElectricPowerQualitySummary(Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	public void exportElectricPowerQualitySummarys(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportElectricPowerUsageSummary(Long retailCustomerId, Long usagePointId,
			Long electricPowerUsageSummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	public void exportElectricPowerUsageSummarys(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportIntervalBlock(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, Long intervalBlockId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	public void exportIntervalBlocks(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, OutputStream stream, ExportFilter exportFilter)
			throws IOException;

	// MeterReading
	//
	public void exportMeterReading(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, OutputStream stream, ExportFilter exportFilter)
			throws IOException;
	
	void exportMeterReadings(ServletOutputStream outputStream,
			ExportFilter exportFilter) throws IOException ;

	void exportMeterReading(long meterReadingId,
			ServletOutputStream outputStream, ExportFilter exportFilter) throws IOException ;
	
	public void exportMeterReadings(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	// ReadingType
	//
	public void exportReadingType(Long retailCustomerId, Long usagePointId,
			Long meterTypeId, Long readingTypeId, OutputStream stream, ExportFilter exportFilter)
			throws IOException;

	public void exportReadingTypes(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportRetailCustomer(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	public void exportRetailCustomers(OutputStream stream, ExportFilter exportFilter)
			throws IOException;

	void exportTimeConfiguration(Long retailCustomerId, Long usagePointId,
			Long timeConfigurationId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	void exportTimeConfigurations(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;
	
	// UsagePoints
	//
	void exportUsagePoint(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportResourceFilter)
			throws IOException;

	void exportUsagePoints(Long retailCustomerId, OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;

	void exportUsagePoint(Long usagePointId,
			OutputStream stream, ExportFilter exportResourceFilter)
			throws IOException;

	void exportUsagePoints(OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;

	void exportApplicationInformations(Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter);

	void exportApplicationInformation(Long retailCustomerId,
			long authorizationId, ServletOutputStream outputStream,
			ExportFilter exportFilter);

	void exportElectricPowerQualitySummarys(ServletOutputStream outputStream,
			ExportFilter exportFilter);

	void exportElectricPowerQualitySummary(long electricPowerQualitySummaryId,
			ServletOutputStream outputStream, ExportFilter exportFilter);

	void exportElectricPowerUsageSummarys(ServletOutputStream outputStream,
			ExportFilter exportFilter);

	void exportElectricPowerUsageSummary(long electricPowerUsageSummaryId,
			ServletOutputStream outputStream, ExportFilter exportFilter);

	void exportIntervalBlocks(ServletOutputStream outputStream,
			ExportFilter exportFilter);

	void exportIntervalBlock(long intervalBlockId,
			ServletOutputStream outputStream, ExportFilter exportFilter);

	void exportReadingTypes(ServletOutputStream outputStream,
			ExportFilter exportFilter);

	void exportReadingType(long readingTypeId,
			ServletOutputStream outputStream, ExportFilter exportFilter);

	void exportTimeConfigurations(ServletOutputStream outputStream,
			ExportFilter exportFilter);

	void exportTimeConfiguration(long timeConfigurationId,
			ServletOutputStream outputStream, ExportFilter exportFilter);

	void exportUsagePointsFull(Long retailCustomerId, ServletOutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	void exportUsagePointFull(Long usagePointId, Long RetailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter)  throws IOException;

}