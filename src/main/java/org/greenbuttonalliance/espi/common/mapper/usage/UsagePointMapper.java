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

import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.greenbuttonalliance.espi.common.dto.usage.UsagePointDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between UsagePointEntity and UsagePointDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    MeterReadingMapper.class,
    UsageSummaryMapper.class,
    ElectricPowerQualitySummaryMapper.class,
    ServiceDeliveryPointMapper.class
})
public interface UsagePointMapper {

    /**
     * Converts a UsagePointEntity to a UsagePointDto.
     * Maps all related entities to their corresponding DTOs.
     * 
     * @param entity the usage point entity
     * @return the usage point DTO
     */
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "roleFlags", source = "roleFlags")
    @Mapping(target = "serviceCategory", source = "serviceCategory")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "serviceDeliveryPoint", source = "serviceDeliveryPoint")
    @Mapping(target = "meterReadings", source = "meterReadings")
    @Mapping(target = "usageSummaries", source = "usageSummaries")
    @Mapping(target = "electricPowerQualitySummaries", source = "electricPowerQualitySummaries")
    UsagePointDto toDto(UsagePointEntity entity);

    /**
     * Converts a UsagePointDto to a UsagePointEntity.
     * Maps all related DTOs to their corresponding entities.
     * 
     * @param dto the usage point DTO
     * @return the usage point entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "roleFlags", source = "roleFlags")
    @Mapping(target = "serviceCategory", source = "serviceCategory")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "serviceDeliveryPoint", source = "serviceDeliveryPoint")
    @Mapping(target = "meterReadings", source = "meterReadings")
    @Mapping(target = "usageSummaries", source = "usageSummaries")
    @Mapping(target = "electricPowerQualitySummaries", source = "electricPowerQualitySummaries")
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "retailCustomer", ignore = true)
    @Mapping(target = "localTimeParameters", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    UsagePointEntity toEntity(UsagePointDto dto);

    /**
     * Updates an existing UsagePointEntity with data from a UsagePointDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "retailCustomer", ignore = true)
    @Mapping(target = "localTimeParameters", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    void updateEntity(UsagePointDto dto, @MappingTarget UsagePointEntity entity);
}