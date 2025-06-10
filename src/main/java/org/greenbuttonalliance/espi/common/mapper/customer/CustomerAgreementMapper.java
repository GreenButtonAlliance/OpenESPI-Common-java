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

package org.greenbuttonalliance.espi.common.mapper.customer;

import org.greenbuttonalliance.espi.common.domain.customer.entity.CustomerAgreementEntity;
import org.greenbuttonalliance.espi.common.dto.customer.CustomerAgreementDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between CustomerAgreementEntity and CustomerAgreementDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring")
public interface CustomerAgreementMapper {

    /**
     * Converts a CustomerAgreementEntity to a CustomerAgreementDto.
     * Maps customer agreement information and service terms.
     * 
     * @param entity the customer agreement entity
     * @return the customer agreement DTO
     */
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "published", source = "published")
    @Mapping(target = "updated", source = "updated")
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "signDate", source = "signDate")
    @Mapping(target = "validityInterval", source = "validityInterval")
    CustomerAgreementDto toDto(CustomerAgreementEntity entity);

    /**
     * Converts a CustomerAgreementDto to a CustomerAgreementEntity.
     * Maps customer agreement information and service terms.
     * 
     * @param dto the customer agreement DTO
     * @return the customer agreement entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", source = "uuid")
    @Mapping(target = "published", source = "published")
    @Mapping(target = "updated", source = "updated")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "signDate", source = "signDate")
    @Mapping(target = "validityInterval", source = "validityInterval")
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    CustomerAgreementEntity toEntity(CustomerAgreementDto dto);

    /**
     * Updates an existing CustomerAgreementEntity with data from a CustomerAgreementDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "customer", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    void updateEntity(CustomerAgreementDto dto, @MappingTarget CustomerAgreementEntity entity);
}