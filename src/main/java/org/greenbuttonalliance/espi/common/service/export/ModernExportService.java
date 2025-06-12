/*
 *
 *    Copyright (c) 2018-2025 Green Button Alliance, Inc.
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

package org.greenbuttonalliance.espi.common.service.export;

import org.greenbuttonalliance.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * Modern export service that replaces the legacy ExportServiceImpl.
 * 
 * This service provides a clean, maintainable API for exporting Green Button resources
 * using the modern DTO-based architecture with MapStruct mappers. It delegates to
 * specialized export services for different resource types.
 * 
 * Benefits over legacy ExportServiceImpl:
 * - Type-safe Entity-DTO conversion
 * - Generic export patterns instead of 40+ specific methods
 * - Clean separation of concerns
 * - Easy to test and maintain
 * - Modern Java patterns and best practices
 */
@Service
public class ModernExportService {

    private final UsagePointExportService usagePointExportService;
    private final MeterReadingExportService meterReadingExportService;
    private final AtomExportService atomExportService;

    public ModernExportService(
        UsagePointExportService usagePointExportService,
        MeterReadingExportService meterReadingExportService,
        AtomExportService atomExportService
    ) {
        this.usagePointExportService = usagePointExportService;
        this.meterReadingExportService = meterReadingExportService;
        this.atomExportService = atomExportService;
    }

    // =============================
    // UsagePoint Export Methods
    // =============================

    /**
     * Exports a single usage point.
     */
    public void exportUsagePoint(Long usagePointId, OutputStream stream) {
        usagePointExportService.exportUsagePoint(usagePointId, stream);
    }

    /**
     * Exports usage points by subscription.
     */
    public void exportUsagePoints(Long subscriptionId, OutputStream stream, ExportFilter filter) {
        usagePointExportService.exportUsagePoints(subscriptionId, stream, filter);
    }

    /**
     * Exports usage points by retail customer.
     */
    public void exportUsagePointsByCustomer(Long retailCustomerId, OutputStream stream, ExportFilter filter) {
        usagePointExportService.exportUsagePointsByCustomer(retailCustomerId, stream, filter);
    }

    // =============================
    // MeterReading Export Methods  
    // =============================

    /**
     * Exports a single meter reading.
     */
    public void exportMeterReading(Long meterReadingId, OutputStream stream) {
        meterReadingExportService.exportMeterReading(meterReadingId, stream);
    }

    /**
     * Exports meter readings by usage point.
     */
    public void exportMeterReadings(Long subscriptionId, Long usagePointId, OutputStream stream, ExportFilter filter) {
        meterReadingExportService.exportMeterReadings(subscriptionId, usagePointId, stream, filter);
    }

    /**
     * Exports a specific meter reading within usage point context.
     */
    public void exportMeterReading(Long subscriptionId, Long usagePointId, Long meterReadingId, OutputStream stream) {
        meterReadingExportService.exportMeterReading(subscriptionId, usagePointId, meterReadingId, stream);
    }

    /**
     * Exports meter readings with full nested details.
     */
    public void exportMeterReadingsWithDetails(Long subscriptionId, Long usagePointId, OutputStream stream, ExportFilter filter) {
        meterReadingExportService.exportMeterReadingsWithDetails(subscriptionId, usagePointId, stream, filter);
    }

    // =============================
    // IntervalBlock Export Methods
    // =============================

    /**
     * Exports a single interval block.
     */
    public void exportIntervalBlock(Object intervalBlockEntity, OutputStream stream) {
        atomExportService.exportIntervalBlock(intervalBlockEntity, stream);
    }

    // =============================
    // UsageSummary Export Methods
    // =============================

    /**
     * Exports a single usage summary.
     */
    public void exportUsageSummary(Object usageSummaryEntity, OutputStream stream) {
        atomExportService.exportUsageSummary(usageSummaryEntity, stream);
    }

    // =============================
    // ElectricPowerQualitySummary Export Methods
    // =============================

    /**
     * Exports a single electric power quality summary.
     */
    public void exportElectricPowerQualitySummary(Object epqsEntity, OutputStream stream) {
        atomExportService.exportElectricPowerQualitySummary(epqsEntity, stream);
    }

    // =============================
    // Customer Export Methods
    // =============================

    /**
     * Exports a single customer.
     */
    public void exportCustomer(Object customerEntity, OutputStream stream) {
        atomExportService.exportCustomer(customerEntity, stream);
    }

    // =============================
    // Generic Export Methods
    // =============================

    /**
     * Generic resource export method.
     * 
     * @param entity the entity to export
     * @param resourceType the resource type (for routing to appropriate mapper)
     * @param stream the output stream
     */
    public void exportGenericResource(Object entity, String resourceType, OutputStream stream) {
        switch (resourceType.toLowerCase()) {
            case "usagepoint":
                atomExportService.exportUsagePoint(entity, stream);
                break;
            case "meterreading":
                atomExportService.exportMeterReading(entity, stream);
                break;
            case "intervalblock":
                atomExportService.exportIntervalBlock(entity, stream);
                break;
            case "usagesummary":
                atomExportService.exportUsageSummary(entity, stream);
                break;
            case "electricpowerqualitysummary":
                atomExportService.exportElectricPowerQualitySummary(entity, stream);
                break;
            case "customer":
                atomExportService.exportCustomer(entity, stream);
                break;
            default:
                throw new IllegalArgumentException("Unknown resource type: " + resourceType);
        }
    }

    // =============================
    // Batch Export Methods
    // =============================

    /**
     * Exports all usage points (admin function).
     */
    public void exportAllUsagePoints(OutputStream stream, ExportFilter filter) {
        usagePointExportService.exportAllUsagePoints(stream, filter);
    }

    /**
     * Exports all meter readings for a customer.
     */
    public void exportAllMeterReadingsByCustomer(Long retailCustomerId, OutputStream stream, ExportFilter filter) {
        meterReadingExportService.exportMeterReadingsByCustomer(retailCustomerId, stream, filter);
    }

    // =============================
    // Migration Helper Methods
    // =============================

    /**
     * Helper method to maintain backward compatibility with legacy ExportService interface.
     * This allows gradual migration from the old service to the new one.
     * 
     * @deprecated Use specific export methods instead
     */
    @Deprecated
    public void legacyExport(String method, Object... params) {
        // This would contain mappings from legacy method names to new methods
        // For example: "exportUsagePoint" -> exportUsagePoint((Long)params[0], (OutputStream)params[1])
        throw new UnsupportedOperationException("Legacy method mapping not implemented: " + method);
    }

    // =============================
    // Utility Methods
    // =============================

    /**
     * Gets the underlying AtomExportService for advanced use cases.
     */
    public AtomExportService getAtomExportService() {
        return atomExportService;
    }

    /**
     * Gets the UsagePointExportService for usage point specific operations.
     */
    public UsagePointExportService getUsagePointExportService() {
        return usagePointExportService;
    }

    /**
     * Gets the MeterReadingExportService for meter reading specific operations.
     */
    public MeterReadingExportService getMeterReadingExportService() {
        return meterReadingExportService;
    }
}