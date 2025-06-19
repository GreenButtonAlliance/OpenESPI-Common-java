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

package org.greenbuttonalliance.espi.common.service.customer;

import org.greenbuttonalliance.espi.common.domain.customer.entity.ServiceLocationEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for ServiceLocationEntity management.
 * 
 * Handles real estate location operations including addresses, access methods,
 * inspection requirements, and service delivery coordination.
 */
public interface ServiceLocationService {

    /**
     * Find all service locations.
     */
    List<ServiceLocationEntity> findAll();

    /**
     * Find service location by ID.
     */
    Optional<ServiceLocationEntity> findById(Long id);

    /**
     * Find service location by UUID.
     */
    Optional<ServiceLocationEntity> findByUuid(String uuid);

    /**
     * Find service locations by outage block.
     */
    List<ServiceLocationEntity> findByOutageBlock(String outageBlock);

    /**
     * Find service locations that need inspection.
     */
    List<ServiceLocationEntity> findLocationsThatNeedInspection();

    /**
     * Find service locations with access problems.
     */
    List<ServiceLocationEntity> findLocationsWithAccessProblems();

    /**
     * Find service locations by main address street.
     */
    List<ServiceLocationEntity> findByMainAddressStreetContaining(String street);

    /**
     * Find service locations by direction.
     */
    List<ServiceLocationEntity> findByDirectionContaining(String direction);

    /**
     * Find service locations by type.
     */
    List<ServiceLocationEntity> findByType(String type);

    /**
     * Find service locations by phone area code.
     */
    List<ServiceLocationEntity> findByPhone1AreaCode(String areaCode);

    /**
     * Find service locations by geo info reference.
     */
    List<ServiceLocationEntity> findByGeoInfoReference(String geoInfoReference);

    /**
     * Save service location.
     */
    ServiceLocationEntity save(ServiceLocationEntity serviceLocation);

    /**
     * Delete service location by ID.
     */
    void deleteById(Long id);

    /**
     * Mark location as needing inspection.
     */
    ServiceLocationEntity markForInspection(Long id, boolean needsInspection);

    /**
     * Update access problem description.
     */
    ServiceLocationEntity updateAccessProblem(Long id, String accessProblem);

    /**
     * Count total service locations.
     */
    long countServiceLocationEntitys();

    /**
     * Count locations needing inspection.
     */
    long countLocationsNeedingInspection();

    /**
     * Count locations with access problems.
     */
    long countLocationsWithAccessProblems();
}