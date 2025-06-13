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

import org.greenbuttonalliance.espi.common.domain.usage.ServiceDeliveryPointEntity;
import org.greenbuttonalliance.espi.common.dto.usage.ServiceDeliveryPointDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between ServiceDeliveryPointEntity and ServiceDeliveryPointDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring")
public interface ServiceDeliveryPointMapper extends BaseIdentifiedObjectMapper {

    /**
     * Converts a ServiceDeliveryPointEntity to a ServiceDeliveryPointDto.
     * Maps service delivery point attributes and connection information.
     * 
     * @param entity the service delivery point entity
     * @return the service delivery point DTO
     */
    @Mapping(target = "uuid", source = "entity", qualifiedByName = "entityUuidToString")
    @Mapping(target = "published", source = "published", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "tariffProfile", ignore = true) // Relationship handled separately
    @Mapping(target = "customerAgreement", ignore = true) // Relationship handled separately
    ServiceDeliveryPointDto toDto(ServiceDeliveryPointEntity entity);

    /**
     * Converts a ServiceDeliveryPointDto to a ServiceDeliveryPointEntity.
     * Maps service delivery point attributes and connection information.
     * 
     * @param dto the service delivery point DTO
     * @return the service delivery point entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true) // UUID is computed from hashedId
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "tariffProfile", ignore = true) // Relationship handled separately
    @Mapping(target = "customerAgreement", ignore = true) // Relationship handled separately
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    ServiceDeliveryPointEntity toEntity(ServiceDeliveryPointDto dto);

    /**
     * Updates an existing ServiceDeliveryPointEntity with data from a ServiceDeliveryPointDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true) // UUID is computed from hashedId
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "tariffProfile", ignore = true) // Relationship handled separately
    @Mapping(target = "customerAgreement", ignore = true) // Relationship handled separately
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    void updateEntity(ServiceDeliveryPointDto dto, @MappingTarget ServiceDeliveryPointEntity entity);
}