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

import org.greenbuttonalliance.espi.common.domain.usage.AggregatedNodeRefEntity;
import org.greenbuttonalliance.espi.common.dto.usage.AggregatedNodeRefDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between AggregatedNodeRefEntity and AggregatedNodeRefDto.
 */
@Mapper(componentModel = "spring", uses = {PnodeRefMapper.class})
public interface AggregatedNodeRefMapper extends BaseIdentifiedObjectMapper {

    /**
     * Converts an AggregatedNodeRefEntity to an AggregatedNodeRefDto.
     * 
     * @param entity the aggregated node reference entity
     * @return the aggregated node reference DTO
     */
    @Mapping(target = "anodeType", source = "anodeType")
    @Mapping(target = "ref", source = "ref")
    @Mapping(target = "startEffectiveDate", source = "startEffectiveDate")
    @Mapping(target = "endEffectiveDate", source = "endEffectiveDate")
    @Mapping(target = "pnodeRef", source = "pnodeRef")
    AggregatedNodeRefDto toDto(AggregatedNodeRefEntity entity);

    /**
     * Converts an AggregatedNodeRefDto to an AggregatedNodeRefEntity.
     * 
     * @param dto the aggregated node reference DTO
     * @return the aggregated node reference entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "anodeType", source = "anodeType")
    @Mapping(target = "ref", source = "ref")
    @Mapping(target = "startEffectiveDate", source = "startEffectiveDate")
    @Mapping(target = "endEffectiveDate", source = "endEffectiveDate")
    @Mapping(target = "pnodeRef", source = "pnodeRef")
    AggregatedNodeRefEntity toEntity(AggregatedNodeRefDto dto);

    /**
     * Updates an existing AggregatedNodeRefEntity with data from an AggregatedNodeRefDto.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usagePoint", ignore = true)
    void updateEntity(AggregatedNodeRefDto dto, @MappingTarget AggregatedNodeRefEntity entity);
}