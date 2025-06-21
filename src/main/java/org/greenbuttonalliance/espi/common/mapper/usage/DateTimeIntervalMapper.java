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

import org.greenbuttonalliance.espi.common.domain.legacy.DateTimeInterval;
import org.greenbuttonalliance.espi.common.dto.usage.DateTimeIntervalDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * MapStruct mapper for converting between DateTimeInterval and DateTimeIntervalDto.
 * 
 * Handles the conversion between the embedded value object used in entities and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring")
public interface DateTimeIntervalMapper {

    /**
     * Converts a DateTimeInterval to a DateTimeIntervalDto.
     * Maps start time and duration appropriately.
     * 
     * @param interval the date/time interval value object
     * @return the date/time interval DTO
     */
    @Mapping(target = "start", source = "start")
    @Mapping(target = "duration", source = "duration")
    DateTimeIntervalDto toDto(DateTimeInterval interval);

    /**
     * Converts a DateTimeIntervalDto to a DateTimeInterval.
     * Maps start time and duration appropriately.
     * 
     * @param dto the date/time interval DTO
     * @return the date/time interval value object
     */
    @Mapping(target = "start", source = "start")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "extension", ignore = true) // Extension field not present in DTO
    DateTimeInterval toEntity(DateTimeIntervalDto dto);
}