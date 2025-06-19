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

import org.greenbuttonalliance.espi.common.domain.usage.PnodeRefEntity;
import org.greenbuttonalliance.espi.common.dto.usage.PnodeRefDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between PnodeRefEntity and PnodeRefDto.
 */
@Mapper(componentModel = "spring")
public interface PnodeRefMapper extends BaseIdentifiedObjectMapper {

    /**
     * Converts a PnodeRefEntity to a PnodeRefDto.
     * 
     * @param entity the pricing node reference entity
     * @return the pricing node reference DTO
     */
    @Mapping(target = "apnodeType", source = "apnodeType")
    @Mapping(target = "ref", source = "ref")
    @Mapping(target = "startEffectiveDate", source = "startEffectiveDate")
    @Mapping(target = "endEffectiveDate", source = "endEffectiveDate")
    PnodeRefDto toDto(PnodeRefEntity entity);

    /**
     * Converts a PnodeRefDto to a PnodeRefEntity.
     * 
     * @param dto the pricing node reference DTO
     * @return the pricing node reference entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "apnodeType", source = "apnodeType")
    @Mapping(target = "ref", source = "ref")
    @Mapping(target = "startEffectiveDate", source = "startEffectiveDate")
    @Mapping(target = "endEffectiveDate", source = "endEffectiveDate")
    PnodeRefEntity toEntity(PnodeRefDto dto);

    /**
     * Updates an existing PnodeRefEntity with data from a PnodeRefDto.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usagePoint", ignore = true)
    void updateEntity(PnodeRefDto dto, @MappingTarget PnodeRefEntity entity);
}