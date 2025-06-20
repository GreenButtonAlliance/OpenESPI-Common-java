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
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.greenbuttonalliance.espi.common.mapper.BaseMapperUtils;
import org.greenbuttonalliance.espi.common.mapper.DateTimeMapper;
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
    DateTimeMapper.class,
    BaseMapperUtils.class,
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
    @Mapping(target = "id", ignore = true) // DTO id field not used
    @Mapping(target = "uuid", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "published", source = "published", qualifiedByName = "localToOffset")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "localToOffset")
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "billingPeriod", source = "billingPeriod")
    @Mapping(target = "billLastPeriod", source = "billLastPeriod")
    @Mapping(target = "billToDate", source = "billToDate")
    @Mapping(target = "costAdditionalLastPeriod", source = "costAdditionalLastPeriod")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "qualityOfReading", source = "qualityOfReading")
    @Mapping(target = "statusTimeStamp", source = "statusTimeStamp", qualifiedByName = "longToOffset")
    UsageSummaryDto toDto(UsageSummaryEntity entity);

    /**
     * Converts a UsageSummaryDto to a UsageSummaryEntity.
     * Maps usage summary data including billing period and cost information.
     * 
     * @param dto the usage summary DTO
     * @return the usage summary entity
     */
    @Mapping(target = "id", source = "uuid", qualifiedByName = "stringToUuid")
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetToLocal")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "offsetToLocal")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "billingPeriod", source = "billingPeriod")
    @Mapping(target = "billLastPeriod", source = "billLastPeriod")
    @Mapping(target = "billToDate", source = "billToDate")
    @Mapping(target = "costAdditionalLastPeriod", source = "costAdditionalLastPeriod")
    @Mapping(target = "currency", source = "currency")
    @Mapping(target = "qualityOfReading", source = "qualityOfReading")
    @Mapping(target = "statusTimeStamp", source = "statusTimeStamp", qualifiedByName = "offsetToLong")
    @Mapping(target = "usagePoint", ignore = true) // Relationship handled separately
    @Mapping(target = "created", ignore = true) // Inherited from IdentifiedObject
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
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetToLocal")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "offsetToLocal")
    @Mapping(target = "statusTimeStamp", source = "statusTimeStamp", qualifiedByName = "offsetToLong")
    @Mapping(target = "usagePoint", ignore = true) // Relationship handled separately
    @Mapping(target = "created", ignore = true) // Inherited from IdentifiedObject
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    void updateEntity(UsageSummaryDto dto, @MappingTarget UsageSummaryEntity entity);
}