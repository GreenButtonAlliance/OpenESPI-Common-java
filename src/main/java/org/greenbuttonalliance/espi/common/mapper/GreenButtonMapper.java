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
import org.springframework.stereotype.Component;

/**
 * Spring component that provides access to all Green Button MapStruct mappers.
 * 
 * This is NOT a MapStruct mapper itself, but a Spring component that aggregates
 * all individual mappers for easy access in services and controllers.
 */
@Component
public class GreenButtonMapper {

    private final UsagePointMapper usagePointMapper;
    private final MeterReadingMapper meterReadingMapper;
    private final IntervalBlockMapper intervalBlockMapper;
    private final IntervalReadingMapper intervalReadingMapper;
    private final ReadingTypeMapper readingTypeMapper;
    private final ReadingQualityMapper readingQualityMapper;
    private final UsageSummaryMapper usageSummaryMapper;
    private final ElectricPowerQualitySummaryMapper electricPowerQualitySummaryMapper;
    private final ServiceDeliveryPointMapper serviceDeliveryPointMapper;
    private final DateTimeIntervalMapper dateTimeIntervalMapper;
    private final CustomerMapper customerMapper;
    private final CustomerAccountMapper customerAccountMapper;
    private final CustomerAgreementMapper customerAgreementMapper;

    public GreenButtonMapper(
            UsagePointMapper usagePointMapper,
            MeterReadingMapper meterReadingMapper,
            IntervalBlockMapper intervalBlockMapper,
            IntervalReadingMapper intervalReadingMapper,
            ReadingTypeMapper readingTypeMapper,
            ReadingQualityMapper readingQualityMapper,
            UsageSummaryMapper usageSummaryMapper,
            ElectricPowerQualitySummaryMapper electricPowerQualitySummaryMapper,
            ServiceDeliveryPointMapper serviceDeliveryPointMapper,
            DateTimeIntervalMapper dateTimeIntervalMapper,
            CustomerMapper customerMapper,
            CustomerAccountMapper customerAccountMapper,
            CustomerAgreementMapper customerAgreementMapper) {
        this.usagePointMapper = usagePointMapper;
        this.meterReadingMapper = meterReadingMapper;
        this.intervalBlockMapper = intervalBlockMapper;
        this.intervalReadingMapper = intervalReadingMapper;
        this.readingTypeMapper = readingTypeMapper;
        this.readingQualityMapper = readingQualityMapper;
        this.usageSummaryMapper = usageSummaryMapper;
        this.electricPowerQualitySummaryMapper = electricPowerQualitySummaryMapper;
        this.serviceDeliveryPointMapper = serviceDeliveryPointMapper;
        this.dateTimeIntervalMapper = dateTimeIntervalMapper;
        this.customerMapper = customerMapper;
        this.customerAccountMapper = customerAccountMapper;
        this.customerAgreementMapper = customerAgreementMapper;
    }

    public UsagePointMapper usagePoint() {
        return usagePointMapper;
    }

    public MeterReadingMapper meterReading() {
        return meterReadingMapper;
    }

    public IntervalBlockMapper intervalBlock() {
        return intervalBlockMapper;
    }

    public IntervalReadingMapper intervalReading() {
        return intervalReadingMapper;
    }

    public ReadingTypeMapper readingType() {
        return readingTypeMapper;
    }

    public ReadingQualityMapper readingQuality() {
        return readingQualityMapper;
    }

    public UsageSummaryMapper usageSummary() {
        return usageSummaryMapper;
    }

    public ElectricPowerQualitySummaryMapper electricPowerQualitySummary() {
        return electricPowerQualitySummaryMapper;
    }

    public ServiceDeliveryPointMapper serviceDeliveryPoint() {
        return serviceDeliveryPointMapper;
    }

    public DateTimeIntervalMapper dateTimeInterval() {
        return dateTimeIntervalMapper;
    }

    public CustomerMapper customer() {
        return customerMapper;
    }

    public CustomerAccountMapper customerAccount() {
        return customerAccountMapper;
    }

    public CustomerAgreementMapper customerAgreement() {
        return customerAgreementMapper;
    }
}