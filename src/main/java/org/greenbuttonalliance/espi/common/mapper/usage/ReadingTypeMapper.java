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

import org.greenbuttonalliance.espi.common.domain.usage.ReadingTypeEntity;
import org.greenbuttonalliance.espi.common.dto.usage.ReadingTypeDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.greenbuttonalliance.espi.common.mapper.BaseMapperUtils;
import org.greenbuttonalliance.espi.common.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between ReadingTypeEntity and ReadingTypeDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    DateTimeMapper.class,
    BaseMapperUtils.class
})
public interface ReadingTypeMapper {

    /**
     * Converts a ReadingTypeEntity to a ReadingTypeDto.
     * Maps all properties including complex reading type specifications.
     * 
     * @param entity the reading type entity
     * @return the reading type DTO
     */
    @Mapping(target = "id", ignore = true) // DTO id field not used
    @Mapping(target = "uuid", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "argument", source = "argument") // Both DTO and Entity use 'argument' field name
    ReadingTypeDto toDto(ReadingTypeEntity entity);

    /**
     * Converts a ReadingTypeDto to a ReadingTypeEntity.
     * Maps all properties including complex reading type specifications.
     * 
     * @param dto the reading type DTO
     * @return the reading type entity
     */
    @Mapping(target = "id", source = "uuid", qualifiedByName = "stringToUuid")
    @Mapping(target = "argument", source = "argument") // Both DTO and Entity use 'argument' field name
    ReadingTypeEntity toEntity(ReadingTypeDto dto);

    /**
     * Updates an existing ReadingTypeEntity with data from a ReadingTypeDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "argument", source = "argument") // Both DTO and Entity use 'argument' field name
    void updateEntity(ReadingTypeDto dto, @MappingTarget ReadingTypeEntity entity);
}