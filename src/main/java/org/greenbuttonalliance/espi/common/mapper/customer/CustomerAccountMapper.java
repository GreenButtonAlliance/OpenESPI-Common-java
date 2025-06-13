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

import org.greenbuttonalliance.espi.common.domain.customer.entity.CustomerAccountEntity;
import org.greenbuttonalliance.espi.common.dto.customer.CustomerAccountDto;
import org.greenbuttonalliance.espi.common.mapper.BaseIdentifiedObjectMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between CustomerAccountEntity and CustomerAccountDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring")
public interface CustomerAccountMapper extends BaseIdentifiedObjectMapper {

    /**
     * Converts a CustomerAccountEntity to a CustomerAccountDto.
     * Maps customer account information and billing details.
     * 
     * @param entity the customer account entity
     * @return the customer account DTO
     */
    @Mapping(target = "uuid", source = "entity", qualifiedByName = "entityUuidToString")
    @Mapping(target = "published", source = "published", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "localDateTimeToOffsetDateTime")
    @Mapping(target = "relatedLinks", ignore = true) // Links handled separately
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "description", source = "description")
    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "accountNumber", ignore = true) // Not in entity
    @Mapping(target = "budgetBill", source = "budgetBill")
    @Mapping(target = "billingCycle", source = "billingCycle")
    @Mapping(target = "lastBillAmount", source = "lastBillAmount")
    @Mapping(target = "transactionDate", ignore = true) // Not in entity
    @Mapping(target = "customer", ignore = true) // Relationship handled separately
    @Mapping(target = "customerAgreements", ignore = true) // Relationship handled separately
    CustomerAccountDto toDto(CustomerAccountEntity entity);

    /**
     * Converts a CustomerAccountDto to a CustomerAccountEntity.
     * Maps customer account information and billing details.
     * 
     * @param dto the customer account DTO
     * @return the customer account entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true) // UUID is computed from hashedId
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "accountId", source = "accountId")
    @Mapping(target = "budgetBill", source = "budgetBill")
    @Mapping(target = "billingCycle", source = "billingCycle")
    @Mapping(target = "lastBillAmount", source = "lastBillAmount")
    @Mapping(target = "notifications", ignore = true) // Relationship handled separately
    @Mapping(target = "contactInfo", ignore = true) // Relationship handled separately
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    CustomerAccountEntity toEntity(CustomerAccountDto dto);

    /**
     * Updates an existing CustomerAccountEntity with data from a CustomerAccountDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "uuid", ignore = true) // UUID is computed from hashedId
    @Mapping(target = "published", source = "published", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "updated", source = "updated", qualifiedByName = "offsetDateTimeToLocalDateTime")
    @Mapping(target = "notifications", ignore = true) // Relationship handled separately
    @Mapping(target = "contactInfo", ignore = true) // Relationship handled separately
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    void updateEntity(CustomerAccountDto dto, @MappingTarget CustomerAccountEntity entity);
}