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

import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.greenbuttonalliance.espi.common.service.UsagePointService;
import org.greenbuttonalliance.espi.common.utils.ExportFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.OutputStream;

/**
 * Service for exporting UsagePoint resources using the modern DTO-based approach.
 * 
 * Replaces legacy usage point export methods with clean, type-safe operations
 * using the AtomExportService and MapStruct mappers.
 */
@Service
public class UsagePointExportService {

    private final AtomExportService atomExportService;
    private final UsagePointService usagePointService;

    @Autowired
    public UsagePointExportService(
        AtomExportService atomExportService,
        UsagePointService usagePointService
    ) {
        this.atomExportService = atomExportService;
        this.usagePointService = usagePointService;
    }

    /**
     * Exports a single usage point by ID.
     * 
     * @param usagePointId the usage point ID
     * @param stream the output stream
     */
    public void exportUsagePoint(Long usagePointId, OutputStream stream) {
        UsagePointEntity entity = usagePointService.findById(usagePointId);
        if (entity == null) {
            throw new IllegalArgumentException("UsagePoint not found: " + usagePointId);
        }
        
        atomExportService.exportUsagePoint(entity, stream);
    }

    /**
     * Exports usage points by subscription ID.
     * TODO: Implement when service interface supports subscription queries
     * 
     * @param subscriptionId the subscription ID
     * @param stream the output stream
     * @param exportFilter optional filter for export parameters
     */
    public void exportUsagePoints(Long subscriptionId, OutputStream stream, ExportFilter exportFilter) {
        throw new UnsupportedOperationException("exportUsagePoints by subscription not yet implemented");
    }

    /**
     * Exports usage points by retail customer ID.
     * TODO: Implement when service interface supports customer collection queries
     * 
     * @param retailCustomerId the retail customer ID
     * @param stream the output stream
     * @param exportFilter optional filter for export parameters
     */
    public void exportUsagePointsByCustomer(Long retailCustomerId, OutputStream stream, ExportFilter exportFilter) {
        throw new UnsupportedOperationException("exportUsagePointsByCustomer not yet implemented");
    }

    /**
     * Exports all usage points (admin/bulk export).
     * TODO: Implement when service interface supports bulk queries
     * 
     * @param stream the output stream
     * @param exportFilter optional filter for export parameters
     */
    public void exportAllUsagePoints(OutputStream stream, ExportFilter exportFilter) {
        throw new UnsupportedOperationException("exportAllUsagePoints not yet implemented");
    }
}