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

import org.greenbuttonalliance.espi.common.domain.customer.entity.MeterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Meter entities.
 * 
 * Manages physical metering device data including serial numbers, form numbers, and installation details.
 */
@Repository
public interface MeterRepository extends JpaRepository<MeterEntity, Long> {

    /**
     * Find meter by UUID (case insensitive).
     */
    @Query("SELECT m FROM MeterEntity m WHERE UPPER(m.uuid) = UPPER(:uuid)")
    Optional<MeterEntity> findByUuid(@Param("uuid") String uuid);

    /**
     * Find meter by serial number.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.serialNumber = :serialNumber")
    Optional<MeterEntity> findBySerialNumber(@Param("serialNumber") String serialNumber);

    /**
     * Find meters by form number.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.formNumber = :formNumber")
    List<MeterEntity> findByFormNumber(@Param("formNumber") String formNumber);

    /**
     * Find meters by UTC number.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.utcNumber = :utcNumber")
    List<MeterEntity> findByUtcNumber(@Param("utcNumber") String utcNumber);

    /**
     * Find virtual meters.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.isVirtual = true")
    List<MeterEntity> findVirtualMeters();

    /**
     * Find physical meters.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.isVirtual = false OR m.isVirtual IS NULL")
    List<MeterEntity> findPhysicalMeters();

    /**
     * Find PAN devices.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.isPan = true")
    List<MeterEntity> findPanDevices();

    /**
     * Find meters by AMR system.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.amrSystem = :amrSystem")
    List<MeterEntity> findByAmrSystem(@Param("amrSystem") String amrSystem);

    /**
     * Find meters by install code.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.installCode = :installCode")
    List<MeterEntity> findByInstallCode(@Param("installCode") String installCode);

    /**
     * Find meters with interval length greater than specified seconds.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.intervalLength > :seconds")
    List<MeterEntity> findByIntervalLengthGreaterThan(@Param("seconds") Long seconds);

    /**
     * Find critical meters.
     */
    @Query("SELECT m FROM MeterEntity m WHERE m.critical = true")
    List<MeterEntity> findCriticalMeters();
}