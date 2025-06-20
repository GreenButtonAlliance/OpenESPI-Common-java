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

import org.greenbuttonalliance.espi.common.domain.usage.MeterReadingEntity;
import org.greenbuttonalliance.espi.common.dto.usage.MeterReadingDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.greenbuttonalliance.espi.common.mapper.BaseMapperUtils;
import org.greenbuttonalliance.espi.common.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between MeterReadingEntity and MeterReadingDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    DateTimeMapper.class,
    BaseMapperUtils.class,
    IntervalBlockMapper.class,
    ReadingTypeMapper.class
})
public interface MeterReadingMapper {

    /**
     * Converts a MeterReadingEntity to a MeterReadingDto.
     * Maps all related entities to their corresponding DTOs.
     * 
     * @param entity the meter reading entity
     * @return the meter reading DTO
     */
    @Mapping(target = "id", ignore = true) // DTO id field not used
    @Mapping(target = "uuid", source = "id", qualifiedByName = "uuidToString")
    MeterReadingDto toDto(MeterReadingEntity entity);

    /**
     * Converts a MeterReadingDto to a MeterReadingEntity.
     * Maps all related DTOs to their corresponding entities.
     * 
     * @param dto the meter reading DTO
     * @return the meter reading entity
     */
    @Mapping(target = "id", source = "uuid", qualifiedByName = "stringToUuid")
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "readingType", ignore = true)
    @Mapping(target = "intervalBlocks", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    MeterReadingEntity toEntity(MeterReadingDto dto);

    /**
     * Updates an existing MeterReadingEntity with data from a MeterReadingDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "readingType", ignore = true)
    @Mapping(target = "intervalBlocks", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    void updateEntity(MeterReadingDto dto, @MappingTarget MeterReadingEntity entity);
}