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
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.greenbuttonalliance.espi.common.mapper.BaseMapperUtils;
import org.greenbuttonalliance.espi.common.mapper.DateTimeMapper;
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
    DateTimeMapper.class,
    BaseMapperUtils.class,
    MeterReadingMapper.class,
    UsageSummaryMapper.class,
    ElectricPowerQualitySummaryMapper.class,
    ServiceDeliveryPointMapper.class,
    PnodeRefMapper.class,
    AggregatedNodeRefMapper.class
})
public interface UsagePointMapper {

    /**
     * Converts a UsagePointEntity to a UsagePointDto.
     * Maps all related entities to their corresponding DTOs.
     * 
     * @param entity the usage point entity
     * @return the usage point DTO
     */
    @Mapping(target = "uuid", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "roleFlags", source = "roleFlags")
    @Mapping(target = "serviceCategory", source = "serviceCategory")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "estimatedLoad", source = "estimatedLoad")
    @Mapping(target = "nominalServiceVoltage", source = "nominalServiceVoltage")
    @Mapping(target = "ratedCurrent", source = "ratedCurrent")
    @Mapping(target = "ratedPower", source = "ratedPower")
    @Mapping(target = "serviceDeliveryPoint", source = "serviceDeliveryPoint")
    @Mapping(target = "pnodeRefs", ignore = true) // TODO: Add mapper implementation
    @Mapping(target = "aggregatedNodeRefs", ignore = true) // TODO: Add mapper implementation
    @Mapping(target = "meterReadings", ignore = true) // Circular dependency - handle separately
    @Mapping(target = "usageSummaries", ignore = true) // Circular dependency - handle separately  
    @Mapping(target = "electricPowerQualitySummaries", ignore = true) // Circular dependency - handle separately
    UsagePointDto toDto(UsagePointEntity entity);

    /**
     * Converts a UsagePointDto to a UsagePointEntity.
     * Maps all related DTOs to their corresponding entities.
     * 
     * @param dto the usage point DTO
     * @return the usage point entity
     */
    @Mapping(target = "id", source = "uuid", qualifiedByName = "stringToUuid")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "roleFlags", source = "roleFlags")
    @Mapping(target = "serviceCategory", source = "serviceCategory")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "estimatedLoad", source = "estimatedLoad")
    @Mapping(target = "nominalServiceVoltage", source = "nominalServiceVoltage")
    @Mapping(target = "ratedCurrent", source = "ratedCurrent")
    @Mapping(target = "ratedPower", source = "ratedPower")
    @Mapping(target = "serviceDeliveryPoint", source = "serviceDeliveryPoint")
    @Mapping(target = "uri", ignore = true)
    @Mapping(target = "pnodeRefs", ignore = true) // TODO: Add mapper implementation
    @Mapping(target = "aggregatedNodeRefs", ignore = true) // TODO: Add mapper implementation
    @Mapping(target = "meterReadings", ignore = true) // Circular dependency - handle separately
    @Mapping(target = "usageSummaries", ignore = true) // Circular dependency - handle separately
    @Mapping(target = "electricPowerQualitySummaries", ignore = true) // Circular dependency - handle separately
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "retailCustomer", ignore = true)
    @Mapping(target = "localTimeParameters", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "subscription", ignore = true)
    UsagePointEntity toEntity(UsagePointDto dto);

    /**
     * Updates an existing UsagePointEntity with data from a UsagePointDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "published", ignore = true) // Managed by entity lifecycle
    @Mapping(target = "updated", ignore = true) // Managed by entity lifecycle
    @Mapping(target = "created", ignore = true) // Inherited from IdentifiedObject
    @Mapping(target = "estimatedLoad", source = "estimatedLoad")
    @Mapping(target = "nominalServiceVoltage", source = "nominalServiceVoltage")
    @Mapping(target = "ratedCurrent", source = "ratedCurrent")
    @Mapping(target = "ratedPower", source = "ratedPower")
    @Mapping(target = "uri", ignore = true) // Managed separately
    @Mapping(target = "pnodeRefs", ignore = true) // Object type - ignore for now
    @Mapping(target = "aggregatedNodeRefs", ignore = true) // Object type - ignore for now
    @Mapping(target = "meterReadings", ignore = true) // Object type - ignore for now
    @Mapping(target = "usageSummaries", ignore = true) // Object type - ignore for now
    @Mapping(target = "electricPowerQualitySummaries", ignore = true) // Object type - ignore for now
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "retailCustomer", ignore = true)
    @Mapping(target = "localTimeParameters", ignore = true)
    @Mapping(target = "subscriptions", ignore = true)
    @Mapping(target = "subscription", ignore = true)
    void updateEntity(UsagePointDto dto, @MappingTarget UsagePointEntity entity);
}