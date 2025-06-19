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

package org.greenbuttonalliance.espi.common.repositories.customer;

import org.greenbuttonalliance.espi.common.domain.customer.entity.ServiceLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for ServiceLocationEntity entities.
 * 
 * Manages real estate location data including addresses, access methods, and service delivery points.
 */
@Repository
public interface ServiceLocationRepository extends JpaRepository<ServiceLocationEntity, Long> {

    /**
     * Find service location by UUID (case insensitive).
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE UPPER(sl.uuid) = UPPER(:uuid)")
    Optional<ServiceLocationEntity> findByUuid(@Param("uuid") String uuid);

    /**
     * Find service locations by outage block.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE sl.outageBlock = :outageBlock")
    List<ServiceLocationEntity> findByOutageBlock(@Param("outageBlock") String outageBlock);

    /**
     * Find service locations that need inspection.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE sl.needsInspection = true")
    List<ServiceLocationEntity> findLocationsThatNeedInspection();

    /**
     * Find service locations with access problems.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE sl.siteAccessProblem IS NOT NULL AND sl.siteAccessProblem != ''")
    List<ServiceLocationEntity> findLocationsWithAccessProblems();

    /**
     * Find service locations by main street address.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE UPPER(sl.mainAddress.street) LIKE UPPER(CONCAT('%', :street, '%'))")
    List<ServiceLocationEntity> findByMainAddressStreetContaining(@Param("street") String street);

    /**
     * Find service locations by direction.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE UPPER(sl.direction) LIKE UPPER(CONCAT('%', :direction, '%'))")
    List<ServiceLocationEntity> findByDirectionContaining(@Param("direction") String direction);

    /**
     * Find service locations by location type.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE sl.type = :type")
    List<ServiceLocationEntity> findByType(@Param("type") String type);

    /**
     * Find service locations by phone1 area code.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE sl.phone1.areaCode = :areaCode")
    List<ServiceLocationEntity> findByPhone1AreaCode(@Param("areaCode") String areaCode);

    /**
     * Find service locations by geo info reference.
     */
    @Query("SELECT sl FROM ServiceLocationEntity sl WHERE sl.geoInfoReference = :geoInfoReference")
    List<ServiceLocationEntity> findByGeoInfoReference(@Param("geoInfoReference") String geoInfoReference);
}