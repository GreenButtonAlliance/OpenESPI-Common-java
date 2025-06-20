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

import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Shared mapping utility methods for all MapStruct mappers.
 * 
 * This interface provides common mapping functions to avoid duplication
 * across different mapper classes and resolve ambiguous method conflicts.
 */
public interface BaseMapperUtils {

    // UUID conversion methods removed to avoid conflicts with BaseIdentifiedObjectMapper
    // Use DateTimeMapper.uuidToString and DateTimeMapper.stringToUuid instead

    /**
     * Extracts UUID from IdentifiedObject as string.
     * Used for DTO mapping where ID is represented as string.
     * 
     * @param entity the entity to extract UUID from
     * @return string representation of entity UUID, or null if entity is null
     */
    @Named("entityToUuidString")
    default String entityToUuidString(IdentifiedObject entity) {
        return entity != null && entity.getId() != null ? entity.getId().toString() : null;
    }

    /**
     * Converts LocalDateTime to OffsetDateTime using UTC offset.
     * 
     * @param localDateTime the LocalDateTime to convert
     * @return OffsetDateTime with UTC offset, or null if input is null
     */
    @Named("localDateTimeToOffsetDateTime")
    default OffsetDateTime localDateTimeToOffsetDateTime(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }

    /**
     * Converts OffsetDateTime to LocalDateTime.
     * 
     * @param offsetDateTime the OffsetDateTime to convert
     * @return LocalDateTime, or null if input is null
     */
    @Named("offsetDateTimeToLocalDateTime")
    default LocalDateTime offsetDateTimeToLocalDateTime(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
    }

    /**
     * Maps UUID to Long for backward compatibility.
     * Note: This is a lossy conversion and should only be used during migration.
     * 
     * @param uuid the UUID to convert
     * @return most significant bits as Long, or null if UUID is null
     */
    @Named("uuidToLong")
    default Long uuidToLong(UUID uuid) {
        return uuid != null ? uuid.getMostSignificantBits() : null;
    }

    /**
     * Maps Long to UUID for backward compatibility.
     * Note: This creates a UUID from only the most significant bits.
     * 
     * @param longValue the Long value to convert
     * @return UUID created from long value, or null if input is null
     */
    @Named("longToUuid")
    default UUID longToUuid(Long longValue) {
        return longValue != null ? new UUID(longValue, 0L) : null;
    }
}