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

package org.greenbuttonalliance.espi.common.mapper;

import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Base mapper interface providing common mapping methods for IdentifiedObject conversions.
 * 
 * This mapper provides standard UUID and DateTime conversion methods that are used
 * across all entity-to-DTO mappings in the OpenESPI system. It ensures consistent
 * handling of ESPI-compliant UUID generation and timezone-aware datetime conversions.
 */
public interface BaseIdentifiedObjectMapper {

    /**
     * Converts entity UUID to DTO UUID string.
     * Entities use UUID primary keys for ESPI compliance.
     * 
     * @param entity any IdentifiedObject entity with getId() method
     * @return UUID string for the DTO, or null if entity is null
     */
    @Named("entityUuidToString")
    default String entityUuidToString(Object entity) {
        if (entity == null) {
            return null;
        }
        
        try {
            // Try getId() method to get UUID directly
            var method = entity.getClass().getMethod("getId");
            Object result = method.invoke(entity);
            return result != null ? result.toString() : null;
        } catch (Exception e) {
            // Fallback to null if reflection fails
            return null;
        }
    }

    /**
     * Converts LocalDateTime from entity to OffsetDateTime for DTO.
     * Assumes UTC timezone for ESPI compliance.
     * 
     * @param localDateTime the entity's LocalDateTime
     * @return OffsetDateTime with UTC offset, or null if input is null
     */
    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }

    /**
     * Converts OffsetDateTime from DTO to LocalDateTime for entity.
     * Converts to UTC and strips offset information.
     * 
     * @param offsetDateTime the DTO's OffsetDateTime
     * @return LocalDateTime in UTC, or null if input is null
     */
    @Named("offsetDateTimeToLocalDateTime")
    default LocalDateTime offsetDateTimeToLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime() : null;
    }

    /**
     * Maps Long value to Long value (pass-through for ID mappings).
     * Used for explicit mapping of relationship IDs.
     * 
     * @param value the source Long value
     * @return the same Long value
     */
    @Named("longToLong")
    default Long longToLong(Long value) {
        return value;
    }

    /**
     * Maps String value to String value (pass-through for string mappings).
     * Used for explicit mapping of string properties.
     * 
     * @param value the source String value
     * @return the same String value
     */
    @Named("stringToString")
    default String stringToString(String value) {
        return value;
    }

    /**
     * Maps Boolean value to Boolean value (pass-through for boolean mappings).
     * Used for explicit mapping of boolean properties.
     * 
     * @param value the source Boolean value
     * @return the same Boolean value
     */
    @Named("booleanToBoolean")
    default Boolean booleanToBoolean(Boolean value) {
        return value;
    }

    /**
     * Extracts relationship ID from entity for DTO mapping.
     * Used for mapping entity relationships to simple ID references in DTOs.
     * 
     * @param entity any entity object with getId() method
     * @return the entity's ID, or null if entity is null
     */
    @Named("entityToId")
    default Long entityToId(Object entity) {
        if (entity == null) {
            return null;
        }
        
        try {
            // Use reflection to call getId() method on any entity
            var method = entity.getClass().getMethod("getId");
            Object result = method.invoke(entity);
            return result instanceof Long ? (Long) result : null;
        } catch (Exception e) {
            // Fallback to null if reflection fails
            return null;
        }
    }

    /**
     * Placeholder for ID to entity mapping (requires repository lookup).
     * This method returns null and should be overridden by specific mappers
     * that have access to repository services for entity lookup.
     * 
     * @param id the entity ID
     * @return null (to be overridden by specific mappers)
     */
    @Named("idToEntity")
    default Object idToEntity(Long id) {
        // This is a placeholder - specific mappers should override this
        // with repository lookups for their specific entity types
        return null;
    }
}