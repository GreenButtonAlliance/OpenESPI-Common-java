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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.legacy.Authorization;
import org.greenbuttonalliance.espi.common.domain.legacy.IdentifiedObject;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;

import jakarta.servlet.ServletOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author jat1
 *
 *         Export (via Green Button XML) any of numerous ESPI resources.
 */
public interface ExportService {

    <T extends IdentifiedObject> void exportResource(Long resourceId,
                                                     Class<T> clazz, OutputStream stream, ExportFilter exportFilter);

    <T extends IdentifiedObject> void exportResources(Class<T> clazz,
                                                      OutputStream stream, ExportFilter exportFilter);

    // ApplicationInformation

    // - ROOT form
    void exportApplicationInformation(Long applicationInformationId,
                                      OutputStream stream, ExportFilter exportFilter) throws IOException;

    void exportApplicationInformations(OutputStream stream,
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
    void exportAuthorization(Long authorizationId, OutputStream stream,
                             ExportFilter exportFilter) throws IOException;

    /**
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportAuthorizations(OutputStream stream,
                              ExportFilter exportFilter) throws IOException;

    /**
     * @param authorization
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportAuthorizations(Authorization authorization,
                              OutputStream stream, ExportFilter exportFilter) throws IOException;

    // - XPath form
    /**
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param authorizationId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportAuthorization(Long retailCustomerId,
                             Long authorizationId, OutputStream stream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportAuthorizations(Long retailCustomerId,
                              OutputStream stream, ExportFilter exportFilter) throws IOException;

    // ElectricPowerQualitySummary
    // - ROOT form

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param electricPowerQualitySummaryId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */

    void exportElectricPowerQualitySummary_Root(Long subscriptionId,
                                                Long electricPowerQualitySummaryId, OutputStream outputStream,
                                                ExportFilter exportFilter) throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportElectricPowerQualitySummarys_Root(Long subscriptionId,
                                                 OutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    // - XPath form
    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param electricPowerQualitySummaryId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportElectricPowerQualitySummary(Long subscriptionId,
                                           Long retailCustomerId, Long usagePointId,
                                           Long electricPowerQualitySummaryId, OutputStream stream,
                                           ExportFilter exportFilter) throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportElectricPowerQualitySummarys(Long subscriptionId,
                                            Long retailCustomerId, Long usagePointId, OutputStream stream,
                                            ExportFilter exportFilter) throws IOException;

    // ElectricPowerUsageSummary
    // - ROOT form

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    // ElectricPowerUsageSummary removed - deprecated resource
    // void exportElectricPowerUsageSummarys_Root(Long subscriptionId,
    //                                           ServletOutputStream outputStream, ExportFilter exportFilter)
    //        throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param electricPowerUsageSummaryId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    // ElectricPowerUsageSummary removed - deprecated resource
    // void exportElectricPowerUsageSummary_Root(Long subscriptionId,
    //                                          long electricPowerUsageSummaryId, ServletOutputStream outputStream,
    //                                          ExportFilter exportFilter) throws IOException;

    // - XPath form

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param electricPowerUsageSummaryId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    // ElectricPowerUsageSummary removed - deprecated resource
    // void exportElectricPowerUsageSummary(Long subscriptionId,
    //                                     Long retailCustomerId, Long usagePointId,
    //                                     Long electricPowerUsageSummaryId, OutputStream stream,
    //                                     ExportFilter exportFilter) throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    // ElectricPowerUsageSummary removed - deprecated resource
    // void exportElectricPowerUsageSummarys(Long subscriptionId,
    //                                      Long retailCustomerId, Long usagePointId, OutputStream stream,
    //                                      ExportFilter exportFilter) throws IOException;

    // ElectricPowerUsageSummary
    // - ROOT form

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsageSummarys_Root(Long subscriptionId,
                                  ServletOutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param usageSummaryId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsageSummary_Root(Long subscriptionId,
                                 long usageSummaryId, ServletOutputStream outputStream,
                                 ExportFilter exportFilter) throws IOException;

    // - XPath form

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param usageSummaryId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsageSummary(Long subscriptionId,
                            Long retailCustomerId, Long usagePointId,
                            Long usageSummaryId, OutputStream stream,
                            ExportFilter exportFilter) throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsageSummarys(Long subscriptionId,
                             Long retailCustomerId, Long usagePointId, OutputStream stream,
                             ExportFilter exportFilter) throws IOException;

    // IntervalBlock
    // - ROOT form
    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportIntervalBlocks_Root(Long subscriptionId,
                                   OutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param intervalBlockId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportIntervalBlock_Root(Long subscriptionId, Long intervalBlockId,
                                  OutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    // - XPath form

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param meterReadingId
     *            Locally unique resource identifier
     * @param intervalBlockId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportIntervalBlock(Long subscriptionId, Long retailCustomerId,
                             Long usagePointId, Long meterReadingId, Long intervalBlockId,
                             OutputStream stream, ExportFilter exportFilter) throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param meterReadingId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportIntervalBlocks(Long subscriptionId,
                              Long retailCustomerId, Long usagePointId, Long meterReadingId,
                              OutputStream stream, ExportFilter exportFilter) throws IOException;

    // MeterReading
    // - ROOT form
    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportMeterReadings_Root(Long subscriptionId,
                                  ServletOutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param meterReadingId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportMeterReading_Root(Long subscriptionId, long meterReadingId,
                                 ServletOutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    // - XPath form
    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param meterReadingId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportMeterReading(Long subscriptionId, Long retailCustomerId,
                            Long usagePointId, Long meterReadingId, OutputStream stream,
                            ExportFilter exportFilter) throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportMeterReadings(Long subscriptionId, Long retailCustomerId,
                             Long usagePointId, OutputStream stream, ExportFilter exportFilter)
            throws IOException;

    // ReadingType
    // - ROOT form
    /**
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportReadingTypes(OutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param readingTypeId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportReadingType(Long readingTypeId, OutputStream outputStream,
                           ExportFilter exportFilter) throws IOException;

    // RetailCustomer
    // - ROOT form

    void exportRetailCustomer(Long subscriptionId,
                              Long retailCustomerId, OutputStream stream,
                              ExportFilter exportFilter) throws IOException;

    void exportRetailCustomers(Long subscriptionId, OutputStream stream,
                               ExportFilter exportFilter) throws IOException;

    // Subscriptions
    //
    // ROOT forms
    // -- TODO: original Pivotal version - used for pub/sub activity b/c of the
    // String/Hashed implication
    void exportSubscription(String subscriptionHashedId,
                            OutputStream stream, ExportFilter exportResourceFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportResourceFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportSubscription(Long subscriptionId, OutputStream stream,
                            ExportFilter exportResourceFilter) throws IOException;

    /**
     * @param stream
     *            An output stream to receive the export
     * @param exportResourceFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportSubscriptions(OutputStream stream,
                             ExportFilter exportResourceFilter) throws IOException;

    // XPath forms

    void exportSubscriptions(Long retailCustomerId, OutputStream stream,
                             ExportFilter exportFilter) throws IOException;

    void exportSubscription(Long retailCustomerId, Long subscriptionId,
                            OutputStream stream, ExportFilter exportFilter) throws IOException;

    // TimeConfiguration
    // - ROOT form

    /**
     * @param timeConfigurationId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportTimeConfiguration(Long timeConfigurationId,
                                 OutputStream stream, ExportFilter exportFilter) throws IOException;

    /**
     * @param stream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportTimeConfigurations(OutputStream stream,
                                  ExportFilter exportFilter) throws IOException;

    // UsagePoints
    // - ROOT form
    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportResourceFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsagePoint_Root(Long subscriptionId, Long usagePointId,
                               OutputStream stream, ExportFilter exportResourceFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportResourceFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsagePoints_Root(Long subscriptionId, OutputStream stream,
                                ExportFilter exportResourceFilter) throws IOException;

    // - XPath form
    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportResourceFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsagePoint(Long subscriptionId, Long retailCustomerId,
                          Long usagePointId, OutputStream stream,
                          ExportFilter exportResourceFilter) throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param stream
     *            An output stream to receive the export
     * @param exportResourceFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsagePoints(Long subscriptionId, Long retailCustomerId,
                           OutputStream stream, ExportFilter exportResourceFilter)
            throws IOException;

    // - Full Export form
    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param retailCustomerId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsagePointsFull(Long subscriptionId, Long retailCustomerId,
                               ServletOutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param RetailCustomerId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportUsagePointFull(Long subscriptionId, Long usagePointId,
                              Long RetailCustomerId, ServletOutputStream outputStream,
                              ExportFilter exportFilter) throws IOException;

    /**
     * @param bulkId
     *            Locally unique resource identifier
     * @param thirdParty
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportBatchBulk(Long bulkId, String thirdParty,
                         OutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportBatchSubscription(Long subscriptionId,
                                 OutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportBatchSubscriptionUsagePoint(Long subscriptionId,
                                           OutputStream outputStream, ExportFilter exportFilter)
            throws IOException;

    /**
     * @param subscriptionId
     *            Locally unique resource identifier
     * @param usagePointId
     *            Locally unique resource identifier
     * @param outputStream
     *            An output stream to receive the export
     * @param exportFilter
     *            A filter to narrow the resource's information scope
     * @throws IOException
     *             A general exception signaled upon various failure conditions
     */
    void exportBatchSubscriptionUsagePoint(Long subscriptionId,
                                           Long usagePointId, OutputStream outputStream,
                                           ExportFilter exportFilter) throws IOException;

}
