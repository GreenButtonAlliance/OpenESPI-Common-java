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

package org.greenbuttonalliance.espi.common.mapper;

import org.greenbuttonalliance.espi.common.mapper.customer.CustomerMapper;
import org.greenbuttonalliance.espi.common.mapper.customer.CustomerAccountMapper;
import org.greenbuttonalliance.espi.common.mapper.customer.CustomerAgreementMapper;
import org.greenbuttonalliance.espi.common.mapper.usage.*;
import org.mapstruct.Mapper;

/**
 * Comprehensive MapStruct mapper that aggregates all entity-DTO mappers.
 * 
 * This central mapper provides access to all Green Button resource mappers
 * and is designed to be injected as a single Spring component for use
 * in services and controllers.
 */
@Mapper(componentModel = "spring", uses = {
    // Usage mappers
    UsagePointMapper.class,
    MeterReadingMapper.class,
    IntervalBlockMapper.class,
    IntervalReadingMapper.class,
    ReadingTypeMapper.class,
    ReadingQualityMapper.class,
    UsageSummaryMapper.class,
    ElectricPowerQualitySummaryMapper.class,
    ServiceDeliveryPointMapper.class,
    DateTimeIntervalMapper.class,
    
    // Customer mappers
    CustomerMapper.class,
    CustomerAccountMapper.class,
    CustomerAgreementMapper.class
})
public interface GreenButtonMapper {

    /**
     * Get the usage point mapper.
     * 
     * @return usage point mapper instance
     */
    UsagePointMapper usagePoint();

    /**
     * Get the meter reading mapper.
     * 
     * @return meter reading mapper instance
     */
    MeterReadingMapper meterReading();

    /**
     * Get the interval block mapper.
     * 
     * @return interval block mapper instance
     */
    IntervalBlockMapper intervalBlock();

    /**
     * Get the interval reading mapper.
     * 
     * @return interval reading mapper instance
     */
    IntervalReadingMapper intervalReading();

    /**
     * Get the reading type mapper.
     * 
     * @return reading type mapper instance
     */
    ReadingTypeMapper readingType();

    /**
     * Get the reading quality mapper.
     * 
     * @return reading quality mapper instance
     */
    ReadingQualityMapper readingQuality();

    /**
     * Get the usage summary mapper.
     * 
     * @return usage summary mapper instance
     */
    UsageSummaryMapper usageSummary();

    /**
     * Get the electric power quality summary mapper.
     * 
     * @return electric power quality summary mapper instance
     */
    ElectricPowerQualitySummaryMapper electricPowerQualitySummary();

    /**
     * Get the service delivery point mapper.
     * 
     * @return service delivery point mapper instance
     */
    ServiceDeliveryPointMapper serviceDeliveryPoint();

    /**
     * Get the date/time interval mapper.
     * 
     * @return date/time interval mapper instance
     */
    DateTimeIntervalMapper dateTimeInterval();

    /**
     * Get the customer mapper.
     * 
     * @return customer mapper instance
     */
    CustomerMapper customer();

    /**
     * Get the customer account mapper.
     * 
     * @return customer account mapper instance
     */
    CustomerAccountMapper customerAccount();

    /**
     * Get the customer agreement mapper.
     * 
     * @return customer agreement mapper instance
     */
    CustomerAgreementMapper customerAgreement();
}