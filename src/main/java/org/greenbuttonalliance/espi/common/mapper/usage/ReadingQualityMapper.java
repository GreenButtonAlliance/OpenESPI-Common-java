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

import org.greenbuttonalliance.espi.common.domain.usage.ReadingQualityEntity;
import org.greenbuttonalliance.espi.common.dto.usage.ReadingQualityDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between ReadingQualityEntity and ReadingQualityDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring")
public interface ReadingQualityMapper {

    /**
     * Converts a ReadingQualityEntity to a ReadingQualityDto.
     * Maps quality indicators and validation information.
     * 
     * @param entity the reading quality entity
     * @return the reading quality DTO
     */
    @Mapping(target = "uuid", ignore = true) // ReadingQuality does not have UUID
    @Mapping(target = "published", ignore = true) // ReadingQuality does not have timestamps
    @Mapping(target = "updated", ignore = true) // ReadingQuality does not have timestamps
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", ignore = true) // ReadingQuality does not have description
    @Mapping(target = "quality", source = "quality")
    ReadingQualityDto toDto(ReadingQualityEntity entity);

    /**
     * Converts a ReadingQualityDto to a ReadingQualityEntity.
     * Maps quality indicators and validation information.
     * 
     * @param dto the reading quality DTO
     * @return the reading quality entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "quality", source = "quality")
    @Mapping(target = "intervalReading", ignore = true) // Relationships handled separately
    ReadingQualityEntity toEntity(ReadingQualityDto dto);

    /**
     * Updates an existing ReadingQualityEntity with data from a ReadingQualityDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "intervalReading", ignore = true) // Relationships handled separately
    void updateEntity(ReadingQualityDto dto, @MappingTarget ReadingQualityEntity entity);
}