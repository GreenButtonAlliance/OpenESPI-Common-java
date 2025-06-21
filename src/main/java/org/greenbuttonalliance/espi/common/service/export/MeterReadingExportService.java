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

import org.greenbuttonalliance.espi.common.domain.legacy.MeterReading;
import org.greenbuttonalliance.espi.common.service.MeterReadingService;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * Service for exporting MeterReading resources using the modern DTO-based approach.
 * 
 * Replaces legacy meter reading export methods with clean, type-safe operations
 * using the AtomExportService and MapStruct mappers.
 */
@Service
public class MeterReadingExportService {

    private final AtomExportService atomExportService;
    private final MeterReadingService meterReadingService;

    @Autowired
    public MeterReadingExportService(
        AtomExportService atomExportService,
        MeterReadingService meterReadingService
    ) {
        this.atomExportService = atomExportService;
        this.meterReadingService = meterReadingService;
    }

    /**
     * Exports a single meter reading by ID.
     * 
     * @param meterReadingId the meter reading ID
     * @param stream the output stream
     */
    public void exportMeterReading(Long meterReadingId, OutputStream stream) {
        // Note: The current MeterReadingService interface doesn't have findById(Long)
        // This would need to be added to the service interface
        throw new UnsupportedOperationException("exportMeterReading by ID not yet implemented - requires service interface enhancement");
    }

    /**
     * Exports meter readings by usage point ID.
     * TODO: Implement when service interface supports usage point queries
     * 
     * @param subscriptionId the subscription ID
     * @param usagePointId the usage point ID
     * @param stream the output stream
     * @param exportFilter optional filter for export parameters
     */
    public void exportMeterReadings(
        Long subscriptionId, 
        Long usagePointId, 
        OutputStream stream, 
        ExportFilter exportFilter
    ) {
        throw new UnsupportedOperationException("exportMeterReadings not yet implemented");
    }

    /**
     * Exports a specific meter reading within a usage point context.
     * 
     * @param subscriptionId the subscription ID
     * @param usagePointId the usage point ID
     * @param meterReadingId the meter reading ID
     * @param stream the output stream
     */
    public void exportMeterReading(
        Long subscriptionId, 
        Long usagePointId, 
        Long meterReadingId, 
        OutputStream stream
    ) {
        MeterReading entity = meterReadingService.findById(subscriptionId, usagePointId, meterReadingId);
        if (entity == null) {
            throw new IllegalArgumentException(
                "MeterReading " + meterReadingId + " not found for UsagePoint " + usagePointId
            );
        }
        
        atomExportService.exportMeterReading(entity, stream);
    }

    /**
     * Exports all meter readings for a retail customer.
     * TODO: Implement when service interface supports customer queries
     * 
     * @param retailCustomerId the retail customer ID
     * @param stream the output stream
     * @param exportFilter optional filter for export parameters
     */
    public void exportMeterReadingsByCustomer(
        Long retailCustomerId, 
        OutputStream stream, 
        ExportFilter exportFilter
    ) {
        throw new UnsupportedOperationException("exportMeterReadingsByCustomer not yet implemented");
    }

    /**
     * Exports meter readings with their complete nested structure.
     * TODO: Implement when service interface supports detailed queries
     * 
     * @param subscriptionId the subscription ID
     * @param usagePointId the usage point ID
     * @param stream the output stream
     * @param exportFilter optional filter for export parameters
     */
    public void exportMeterReadingsWithDetails(
        Long subscriptionId, 
        Long usagePointId, 
        OutputStream stream, 
        ExportFilter exportFilter
    ) {
        throw new UnsupportedOperationException("exportMeterReadingsWithDetails not yet implemented");
    }
}