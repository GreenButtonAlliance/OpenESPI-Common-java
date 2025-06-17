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

import org.greenbuttonalliance.espi.common.domain.usage.IntervalReadingEntity;
import org.greenbuttonalliance.espi.common.dto.usage.IntervalReadingDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between IntervalReadingEntity and IntervalReadingDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    ReadingQualityMapper.class,
    DateTimeIntervalMapper.class
})
public interface IntervalReadingMapper {

    /**
     * Converts an IntervalReadingEntity to an IntervalReadingDto.
     * Maps all related entities to their corresponding DTOs.
     * 
     * @param entity the interval reading entity
     * @return the interval reading DTO
     */
    @Mapping(target = "uuid", ignore = true) // IntervalReading does not have UUID
    @Mapping(target = "published", ignore = true) // IntervalReading does not have timestamps
    @Mapping(target = "updated", ignore = true) // IntervalReading does not have timestamps
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", ignore = true) // IntervalReading does not have description
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "currency", ignore = true) // IntervalReading does not have currency
    @Mapping(target = "value", source = "value")
    @Mapping(target = "timePeriod", source = "timePeriod")
    @Mapping(target = "readingQualities", source = "readingQualities")
    @Mapping(target = "consumptionTier", source = "consumptionTier")
    @Mapping(target = "tou", source = "tou")
    @Mapping(target = "cpp", source = "cpp")
    IntervalReadingDto toDto(IntervalReadingEntity entity);

    /**
     * Converts an IntervalReadingDto to an IntervalReadingEntity.
     * Maps all related DTOs to their corresponding entities.
     * 
     * @param dto the interval reading DTO
     * @return the interval reading entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "cost", source = "cost")
    @Mapping(target = "value", source = "value")
    @Mapping(target = "timePeriod", source = "timePeriod")
    @Mapping(target = "readingQualities", source = "readingQualities")
    @Mapping(target = "consumptionTier", source = "consumptionTier")
    @Mapping(target = "tou", source = "tou")
    @Mapping(target = "cpp", source = "cpp")
    @Mapping(target = "intervalBlock", ignore = true) // Relationships handled separately
    IntervalReadingEntity toEntity(IntervalReadingDto dto);

    /**
     * Updates an existing IntervalReadingEntity with data from an IntervalReadingDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "intervalBlock", ignore = true) // Relationships handled separately
    void updateEntity(IntervalReadingDto dto, @MappingTarget IntervalReadingEntity entity);
}