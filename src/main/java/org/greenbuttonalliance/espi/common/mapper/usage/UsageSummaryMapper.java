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

package org.greenbuttonalliance.espi.common.mapper.usage;

import org.greenbuttonalliance.espi.common.domain.usage.UsageSummaryEntity;
import org.greenbuttonalliance.espi.common.dto.usage.UsageSummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between UsageSummaryEntity and UsageSummaryDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    DateTimeIntervalMapper.class
})
public interface UsageSummaryMapper {

    /**
     * Converts a UsageSummaryEntity to a UsageSummaryDto.
     * Maps usage summary data including billing period and cost information.
     * 
     * @param entity the usage summary entity
     * @return the usage summary DTO
     */
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "published", source = "published")
    @Mapping(target = "updated", source = "updated")
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "billingPeriod", source = "billingPeriod")
    @Mapping(target = "billLastPeriod", source = "billLastPeriod")
    @Mapping(target = "billToDate", source = "billToDate")
    @Mapping(target = "costAdditionalLastPeriod", source = "costAdditionalLastPeriod")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "currentBillingPeriodOverAllConsumption", source = "currentBillingPeriodOverAllConsumption")
    @Mapping(target = "currentDayLastYearNetConsumption", source = "currentDayLastYearNetConsumption")
    @Mapping(target = "currentDayNetConsumption", source = "currentDayNetConsumption")
    @Mapping(target = "currentDayOverallConsumption", source = "currentDayOverallConsumption")
    @Mapping(target = "peakDemand", source = "peakDemand")
    @Mapping(target = "previousDayLastYearOverallConsumption", source = "previousDayLastYearOverallConsumption")
    @Mapping(target = "previousDayNetConsumption", source = "previousDayNetConsumption")
    @Mapping(target = "previousDayOverallConsumption", source = "previousDayOverallConsumption")
    @Mapping(target = "qualityOfReading", source = "qualityOfReading")
    @Mapping(target = "ratchetDemand", source = "ratchetDemand")
    @Mapping(target = "ratchetDemandPeriod", source = "ratchetDemandPeriod")
    @Mapping(target = "status", source = "status")
    UsageSummaryDto toDto(UsageSummaryEntity entity);

    /**
     * Converts a UsageSummaryDto to a UsageSummaryEntity.
     * Maps usage summary data including billing period and cost information.
     * 
     * @param dto the usage summary DTO
     * @return the usage summary entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "published", source = "published")
    @Mapping(target = "updated", source = "updated")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "billingPeriod", source = "billingPeriod")
    @Mapping(target = "billLastPeriod", source = "billLastPeriod")
    @Mapping(target = "billToDate", source = "billToDate")
    @Mapping(target = "costAdditionalLastPeriod", source = "costAdditionalLastPeriod")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "currentBillingPeriodOverAllConsumption", source = "currentBillingPeriodOverAllConsumption")
    @Mapping(target = "currentDayLastYearNetConsumption", source = "currentDayLastYearNetConsumption")
    @Mapping(target = "currentDayNetConsumption", source = "currentDayNetConsumption")
    @Mapping(target = "currentDayOverallConsumption", source = "currentDayOverallConsumption")
    @Mapping(target = "peakDemand", source = "peakDemand")
    @Mapping(target = "previousDayLastYearOverallConsumption", source = "previousDayLastYearOverallConsumption")
    @Mapping(target = "previousDayNetConsumption", source = "previousDayNetConsumption")
    @Mapping(target = "previousDayOverallConsumption", source = "previousDayOverallConsumption")
    @Mapping(target = "qualityOfReading", source = "qualityOfReading")
    @Mapping(target = "ratchetDemand", source = "ratchetDemand")
    @Mapping(target = "ratchetDemandPeriod", source = "ratchetDemandPeriod")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    UsageSummaryEntity toEntity(UsageSummaryDto dto);

    /**
     * Updates an existing UsageSummaryEntity with data from a UsageSummaryDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    void updateEntity(UsageSummaryDto dto, @MappingTarget UsageSummaryEntity entity);
}