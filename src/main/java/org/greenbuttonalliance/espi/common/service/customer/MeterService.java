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

import org.greenbuttonalliance.espi.common.domain.customer.entity.MeterEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for Meter management.
 * 
 * Handles physical metering device operations including serial numbers,
 * form numbers, AMR systems, and device characteristics.
 */
public interface MeterService {

    /**
     * Find all meters.
     */
    List<MeterEntity> findAll();

    /**
     * Find meter by ID.
     */
    Optional<MeterEntity> findById(Long id);

    /**
     * Find meter by UUID.
     */
    Optional<MeterEntity> findByUuid(String uuid);

    /**
     * Find meter by serial number.
     */
    Optional<MeterEntity> findBySerialNumber(String serialNumber);

    /**
     * Find meters by form number.
     */
    List<MeterEntity> findByFormNumber(String formNumber);

    /**
     * Find meters by UTC number.
     */
    List<MeterEntity> findByUtcNumber(String utcNumber);

    /**
     * Find virtual meters.
     */
    List<MeterEntity> findVirtualMeters();

    /**
     * Find physical meters.
     */
    List<MeterEntity> findPhysicalMeters();

    /**
     * Find PAN devices.
     */
    List<MeterEntity> findPanDevices();

    /**
     * Find meters by AMR system.
     */
    List<MeterEntity> findByAmrSystem(String amrSystem);

    /**
     * Find meters by install code.
     */
    List<MeterEntity> findByInstallCode(String installCode);

    /**
     * Find meters with interval length greater than specified seconds.
     */
    List<MeterEntity> findByIntervalLengthGreaterThan(Long seconds);

    /**
     * Find critical meters.
     */
    List<MeterEntity> findCriticalMeters();

    /**
     * Save meter.
     */
    MeterEntity save(MeterEntity meter);

    /**
     * Delete meter by ID.
     */
    void deleteById(Long id);

    /**
     * Check if meter exists by serial number.
     */
    boolean existsBySerialNumber(String serialNumber);

    /**
     * Count total meters.
     */
    long countMeters();

    /**
     * Count virtual meters.
     */
    long countVirtualMeters();

    /**
     * Count physical meters.
     */
    long countPhysicalMeters();
}