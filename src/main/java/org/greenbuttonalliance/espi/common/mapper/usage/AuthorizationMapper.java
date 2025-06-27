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

import org.greenbuttonalliance.espi.common.domain.usage.AuthorizationEntity;
import org.greenbuttonalliance.espi.common.dto.usage.AuthorizationDto;
import org.greenbuttonalliance.espi.common.mapper.BaseMapperUtils;
import org.greenbuttonalliance.espi.common.mapper.DateTimeMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * MapStruct mapper for converting between AuthorizationEntity and AuthorizationDto.
 * 
 * Handles the conversion between the JPA entity used for persistence and the DTO 
 * used for JAXB XML marshalling in the Green Button API.
 */
@Mapper(componentModel = "spring", uses = {
    DateTimeMapper.class,
    BaseMapperUtils.class
})
public interface AuthorizationMapper {

    /**
     * Converts an AuthorizationEntity to an AuthorizationDto.
     * Maps all OAuth 2.0 fields for XML marshalling.
     * 
     * @param entity the authorization entity
     * @return the authorization DTO
     */
    @Mapping(target = "uuid", source = "id", qualifiedByName = "uuidToString")
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "authorizationUri", source = "authorizationURI")
    @Mapping(target = "applicationInformationId", ignore = true) // Handle separately
    @Mapping(target = "retailCustomerId", ignore = true) // Handle separately
    @Mapping(target = "resourceURI", source = "resourceURI")
    @Mapping(target = "scope", source = "scope")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "expiresIn", source = "expiresIn")
    @Mapping(target = "grantType", source = "grantType")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "tokenType", source = "tokenType")
    @Mapping(target = "thirdParty", source = "thirdParty")
    @Mapping(target = "ppid", ignore = true) // Field not found in entity
    @Mapping(target = "authorizationCode", source = "code")
    AuthorizationDto toDto(AuthorizationEntity entity);

    /**
     * Converts an AuthorizationDto to an AuthorizationEntity.
     * Maps all OAuth 2.0 fields for persistence.
     * 
     * @param dto the authorization DTO
     * @return the authorization entity
     */
    @Mapping(target = "id", source = "uuid", qualifiedByName = "stringToUuid")
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "authorizationURI", source = "authorizationUri")
    @Mapping(target = "applicationInformation", ignore = true) // Complex mapping, handle separately
    @Mapping(target = "retailCustomer", ignore = true) // Complex mapping, handle separately
    @Mapping(target = "resourceURI", source = "resourceURI")
    @Mapping(target = "scope", source = "scope")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "expiresIn", source = "expiresIn")
    @Mapping(target = "grantType", source = "grantType")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "tokenType", source = "tokenType")
    @Mapping(target = "thirdParty", source = "thirdParty")
    @Mapping(target = "code", source = "authorizationCode")
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "subscription", ignore = true)
    AuthorizationEntity toEntity(AuthorizationDto dto);

    /**
     * Updates an existing AuthorizationEntity with data from an AuthorizationDto.
     * Useful for merge operations where the entity ID should be preserved.
     * 
     * @param dto the source DTO
     * @param entity the target entity to update
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "published", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "relatedLinks", ignore = true)
    @Mapping(target = "selfLink", ignore = true)
    @Mapping(target = "upLink", ignore = true)
    @Mapping(target = "retailCustomer", ignore = true)
    @Mapping(target = "applicationInformation", ignore = true)
    @Mapping(target = "subscription", ignore = true)
    void updateEntity(AuthorizationDto dto, @MappingTarget AuthorizationEntity entity);
}