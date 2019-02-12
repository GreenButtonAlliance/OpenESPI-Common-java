/*
 *     Copyright (c) 2018-2019 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.Authorization;
import org.greenbuttonalliance.espi.common.domain.IdentifiedObject;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;

import javax.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author jat1
 *
 *         Export (via Green Button XML) any of numerous ESPI resources.
 */
public interface ExportService {

	public <T extends IdentifiedObject> void exportResource(Long resourceId,
			Class<T> clazz, OutputStream stream, ExportFilter exportFilter);

	public <T extends IdentifiedObject> void exportResources(Class<T> clazz,
			OutputStream stream, ExportFilter exportFilter);

	// ApplicationInformation

	// - ROOT form
	public void exportApplicationInformation(Long applicationInformationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	public void exportApplicationInformations(OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	// Authorization
	// - ROOT form
	/**
	 * @param authorizationId
	 *            Locally unique resource identifier
	 * @param stream
	 *            An output stream to receive the export
	 * @param exportFilter
	 *            A filter to narrow the resource's information scope
	 * @throws IOException
	 *             A general exception signaled upon various failure conditions
	 */
	public void exportAuthorization(Long authorizationId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	/**
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportAuthorizations(OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	/**
	 * @param authorization
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportAuthorizations(Authorization authorization,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	// - XPath form
	/**
	 * @param retailCustomerId
	 * @param authorizationId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportAuthorization(Long retailCustomerId,
			Long authorizationId, OutputStream stream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param retailCustomerId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportAuthorizations(Long retailCustomerId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	// ElectricPowerQualitySummary
	// - ROOT form

	/**
	 * @param subscriptionId
	 * @param electricPowerQualitySummaryId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */

	void exportElectricPowerQualitySummary_Root(Long subscriptionId,
			Long electricPowerQualitySummaryId, OutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	/**
	 * @param subscriptionId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportElectricPowerQualitySummarys_Root(Long subscriptionId,
			OutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	// - XPath form
	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param electricPowerQualitySummaryId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportElectricPowerQualitySummary(Long subscriptionId,
			Long retailCustomerId, Long usagePointId,
			Long electricPowerQualitySummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportElectricPowerQualitySummarys(Long subscriptionId,
			Long retailCustomerId, Long usagePointId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	// ElectricPowerUsageSummary
	// - ROOT form

	/**
	 * @param subscriptionId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportElectricPowerUsageSummarys_Root(Long subscriptionId,
			ServletOutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param electricPowerUsageSummaryId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportElectricPowerUsageSummary_Root(Long subscriptionId,
			long electricPowerUsageSummaryId, ServletOutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	// - XPath form

	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param electricPowerUsageSummaryId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportElectricPowerUsageSummary(Long subscriptionId,
			Long retailCustomerId, Long usagePointId,
			Long electricPowerUsageSummaryId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportElectricPowerUsageSummarys(Long subscriptionId,
			Long retailCustomerId, Long usagePointId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	// IntervalBlock
	// - ROOT form
	/**
	 * @param subscriptionId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportIntervalBlocks_Root(Long subscriptionId,
			OutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param intervalBlockId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportIntervalBlock_Root(Long subscriptionId, Long intervalBlockId,
			OutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	// - XPath form

	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param meterReadingId
	 * @param intervalBlockId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportIntervalBlock(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, Long meterReadingId, Long intervalBlockId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param meterReadingId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportIntervalBlocks(Long subscriptionId,
			Long retailCustomerId, Long usagePointId, Long meterReadingId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	// MeterReading
	// - ROOT form
	/**
	 * @param subscriptionId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportMeterReadings_Root(Long subscriptionId,
			ServletOutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param meterReadingId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportMeterReading_Root(Long subscriptionId, long meterReadingId,
			ServletOutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	// - XPath form
	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param meterReadingId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportMeterReading(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, Long meterReadingId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportMeterReadings(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, OutputStream stream, ExportFilter exportFilter)
			throws IOException;

	// ReadingType
	// - ROOT form
	/**
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportReadingTypes(OutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param readingTypeId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportReadingType(Long readingTypeId, OutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	// RetailCustomer
	// - ROOT form

	public void exportRetailCustomer(Long subscriptionId,
			Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	public void exportRetailCustomers(Long subscriptionId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	// Subscriptions
	//
	// ROOT forms
	// -- TODO: original Pivotal version - used for pub/sub activity b/c of the
	// String/Hashed implication
	public void exportSubscription(String subscriptionHashedId,
			OutputStream stream, ExportFilter exportResourceFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param stream
	 * @param exportResourceFilter
	 * @throws IOException
	 */
	public void exportSubscription(Long subscriptionId, OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;

	/**
	 * @param stream
	 * @param exportResourceFilter
	 * @throws IOException
	 */
	public void exportSubscriptions(OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;

	// XPath forms

	public void exportSubscriptions(Long retailCustomerId, OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	public void exportSubscription(Long retailCustomerId, Long subscriptionId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	// TimeConfiguration
	// - ROOT form

	/**
	 * @param timeConfigurationId
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportTimeConfiguration(Long timeConfigurationId,
			OutputStream stream, ExportFilter exportFilter) throws IOException;

	/**
	 * @param stream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportTimeConfigurations(OutputStream stream,
			ExportFilter exportFilter) throws IOException;

	// UsagePoints
	// - ROOT form
	/**
	 * @param subscriptionId
	 * @param usagePointId
	 * @param stream
	 * @param exportResourceFilter
	 * @throws IOException
	 */
	void exportUsagePoint_Root(Long subscriptionId, Long usagePointId,
			OutputStream stream, ExportFilter exportResourceFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param stream
	 * @param exportResourceFilter
	 * @throws IOException
	 */
	void exportUsagePoints_Root(Long subscriptionId, OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;

	// - XPath form
	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param usagePointId
	 * @param stream
	 * @param exportResourceFilter
	 * @throws IOException
	 */
	void exportUsagePoint(Long subscriptionId, Long retailCustomerId,
			Long usagePointId, OutputStream stream,
			ExportFilter exportResourceFilter) throws IOException;

	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param stream
	 * @param exportResourceFilter
	 * @throws IOException
	 */
	void exportUsagePoints(Long subscriptionId, Long retailCustomerId,
			OutputStream stream, ExportFilter exportResourceFilter)
			throws IOException;

	// - Full Export form
	/**
	 * @param subscriptionId
	 * @param retailCustomerId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportUsagePointsFull(Long subscriptionId, Long retailCustomerId,
			ServletOutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param usagePointId
	 * @param RetailCustomerId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	void exportUsagePointFull(Long subscriptionId, Long usagePointId,
			Long RetailCustomerId, ServletOutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

	/**
	 * @param bulkId
	 * @param thirdParty
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportBatchBulk(Long bulkId, String thirdParty,
			OutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportBatchSubscription(Long subscriptionId,
			OutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportBatchSubscriptionUsagePoint(Long subscriptionId,
			OutputStream outputStream, ExportFilter exportFilter)
			throws IOException;

	/**
	 * @param subscriptionId
	 * @param usagePointId
	 * @param outputStream
	 * @param exportFilter
	 * @throws IOException
	 */
	public void exportBatchSubscriptionUsagePoint(Long subscriptionId,
			Long usagePointId, OutputStream outputStream,
			ExportFilter exportFilter) throws IOException;

}
