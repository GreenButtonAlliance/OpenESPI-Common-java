package org.energyos.espi.common.service;

import org.energyos.espi.common.utils.ExportFilter;

import java.io.IOException;
import java.io.OutputStream;

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

	public void exportMeterReading(Long retailCustomerId, Long usagePointId,
			Long meterReadingId, OutputStream stream, ExportFilter exportFilter)
			throws IOException;

	public void exportMeterReadings(Long retailCustomerId, Long usagePointId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportReadingType(Long retailCustomerId, Long usagePointId,
			Long meterTypeId, OutputStream stream, ExportFilter exportFilter)
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
}