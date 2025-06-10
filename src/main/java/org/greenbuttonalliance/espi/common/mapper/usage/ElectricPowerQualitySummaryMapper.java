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

import org.greenbuttonalliance.espi.common.domain.usage.ElectricPowerQualitySummaryEntity;
import org.greenbuttonalliance.espi.common.dto.usage.ElectricPowerQualitySummaryDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between ElectricPowerQualitySummaryEntity and ElectricPowerQualitySummaryDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    DateTimeIntervalMapper.class
})
public interface ElectricPowerQualitySummaryMapper {

    /**
     * Converts an ElectricPowerQualitySummaryEntity to an ElectricPowerQualitySummaryDto.
     * Maps power quality metrics and measurement information.
     * 
     * @param entity the electric power quality summary entity
     * @return the electric power quality summary DTO
     */
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "published", source = "published")
    @Mapping(target = "updated", source = "updated")
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "flickerPlt", source = "flickerPlt")
    @Mapping(target = "flickerPst", source = "flickerPst")
    @Mapping(target = "harmonicVoltage", source = "harmonicVoltage")
    @Mapping(target = "longInterruptions", source = "longInterruptions")
    @Mapping(target = "mainsVoltage", source = "mainsVoltage")
    @Mapping(target = "measurementProtocol", source = "measurementProtocol")
    @Mapping(target = "powerFrequency", source = "powerFrequency")
    @Mapping(target = "rapidVoltageChanges", source = "rapidVoltageChanges")
    @Mapping(target = "shortInterruptions", source = "shortInterruptions")
    @Mapping(target = "summaryInterval", source = "summaryInterval")
    @Mapping(target = "supplyVoltageDips", source = "supplyVoltageDips")
    @Mapping(target = "supplyVoltageImbalance", source = "supplyVoltageImbalance")
    @Mapping(target = "supplyVoltageVariations", source = "supplyVoltageVariations")
    @Mapping(target = "tempOvervoltage", source = "tempOvervoltage")
    ElectricPowerQualitySummaryDto toDto(ElectricPowerQualitySummaryEntity entity);

    /**
     * Converts an ElectricPowerQualitySummaryDto to an ElectricPowerQualitySummaryEntity.
     * Maps power quality metrics and measurement information.
     * 
     * @param dto the electric power quality summary DTO
     * @return the electric power quality summary entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "published", source = "published")
    @Mapping(target = "updated", source = "updated")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "flickerPlt", source = "flickerPlt")
    @Mapping(target = "flickerPst", source = "flickerPst")
    @Mapping(target = "harmonicVoltage", source = "harmonicVoltage")
    @Mapping(target = "longInterruptions", source = "longInterruptions")
    @Mapping(target = "mainsVoltage", source = "mainsVoltage")
    @Mapping(target = "measurementProtocol", source = "measurementProtocol")
    @Mapping(target = "powerFrequency", source = "powerFrequency")
    @Mapping(target = "rapidVoltageChanges", source = "rapidVoltageChanges")
    @Mapping(target = "shortInterruptions", source = "shortInterruptions")
    @Mapping(target = "summaryInterval", source = "summaryInterval")
    @Mapping(target = "supplyVoltageDips", source = "supplyVoltageDips")
    @Mapping(target = "supplyVoltageImbalance", source = "supplyVoltageImbalance")
    @Mapping(target = "supplyVoltageVariations", source = "supplyVoltageVariations")
    @Mapping(target = "tempOvervoltage", source = "tempOvervoltage")
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    ElectricPowerQualitySummaryEntity toEntity(ElectricPowerQualitySummaryDto dto);

    /**
     * Updates an existing ElectricPowerQualitySummaryEntity with data from an ElectricPowerQualitySummaryDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "usagePoint", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    void updateEntity(ElectricPowerQualitySummaryDto dto, @MappingTarget ElectricPowerQualitySummaryEntity entity);
}