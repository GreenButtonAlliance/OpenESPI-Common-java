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

import org.greenbuttonalliance.espi.common.domain.usage.IntervalBlockEntity;
import org.greenbuttonalliance.espi.common.dto.usage.IntervalBlockDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.greenbuttonalliance.espi.common.mapper.BaseMapperUtils;
import org.greenbuttonalliance.espi.common.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between IntervalBlockEntity and IntervalBlockDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    DateTimeMapper.class,
    BaseMapperUtils.class,
    IntervalReadingMapper.class,
    DateTimeIntervalMapper.class
})
public interface IntervalBlockMapper {

    /**
     * Converts an IntervalBlockEntity to an IntervalBlockDto.
     * Maps all related entities to their corresponding DTOs.
     * 
     * @param entity the interval block entity
     * @return the interval block DTO
     */
    @Mapping(target = "id", ignore = true) // DTO id field not used
    @Mapping(target = "uuid", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "published", source = "published", qualifiedByName = "localToOffset")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "localToOffset")
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "interval", source = "interval")
    @Mapping(target = "intervalReadings", source = "intervalReadings")
    IntervalBlockDto toDto(IntervalBlockEntity entity);

    /**
     * Converts an IntervalBlockDto to an IntervalBlockEntity.
     * Maps all related DTOs to their corresponding entities.
     * 
     * @param dto the interval block DTO
     * @return the interval block entity
     */
    @Mapping(target = "id", source = "uuid", qualifiedByName = "stringToUuid")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetToLocal")
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "interval", source = "interval")
    @Mapping(target = "intervalReadings", source = "intervalReadings")
    @Mapping(target = "meterReading", ignore = true) // Relationships handled separately
    IntervalBlockEntity toEntity(IntervalBlockDto dto);

    /**
     * Updates an existing IntervalBlockEntity with data from an IntervalBlockDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetToLocal")
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "meterReading", ignore = true) // Relationships handled separately
    void updateEntity(IntervalBlockDto dto, @MappingTarget IntervalBlockEntity entity);
}