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

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

/**
 * Base mapper for common datetime conversions needed by all MapStruct mappers.
 * 
 * Provides conversion methods between LocalDateTime and OffsetDateTime types.
 */
@Mapper(componentModel = "spring")
public interface DateTimeMapper {

    /**
     * Converts LocalDateTime to OffsetDateTime using UTC offset.
     * @param localDateTime the local datetime
     * @return the offset datetime, or null if input is null
     */
    @Named("localToOffset")
    default OffsetDateTime localToOffset(LocalDateTime localDateTime) {
        return localDateTime != null ? localDateTime.atOffset(ZoneOffset.UTC) : null;
    }

    /**
     * Converts OffsetDateTime to LocalDateTime.
     * @param offsetDateTime the offset datetime
     * @return the local datetime, or null if input is null
     */
    @Named("offsetToLocal")
    default LocalDateTime offsetToLocal(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toLocalDateTime() : null;
    }

    /**
     * Default mapping method for LocalDateTime to OffsetDateTime.
     * Used by MapStruct when no explicit qualification is provided.
     */
    default OffsetDateTime map(LocalDateTime localDateTime) {
        return localToOffset(localDateTime);
    }

    /**
     * Default mapping method for OffsetDateTime to LocalDateTime.
     * Used by MapStruct when no explicit qualification is provided.
     */
    default LocalDateTime map(OffsetDateTime offsetDateTime) {
        return offsetToLocal(offsetDateTime);
    }

    /**
     * Converts OffsetDateTime to Long timestamp.
     * @param offsetDateTime the offset datetime
     * @return the timestamp in milliseconds, or null if input is null
     */
    @Named("offsetToLong")
    default Long mapToLong(OffsetDateTime offsetDateTime) {
        return offsetDateTime != null ? offsetDateTime.toInstant().toEpochMilli() : null;
    }

    /**
     * Converts Long timestamp to OffsetDateTime.
     * @param timestamp the timestamp in milliseconds
     * @return the offset datetime, or null if input is null
     */
    @Named("longToOffset")
    default OffsetDateTime mapFromLong(Long timestamp) {
        return timestamp != null ? OffsetDateTime.ofInstant(
            java.time.Instant.ofEpochMilli(timestamp), ZoneOffset.UTC) : null;
    }

    /**
     * Converts UUID object to String.
     * @param uuid the UUID object
     * @return string representation of UUID
     */
    @Named("uuidToString")
    default String uuidToString(UUID uuid) {
        return uuid != null ? uuid.toString().toUpperCase() : null;
    }

    /**
     * Converts String to UUID object.
     * @param uuidString the UUID string
     * @return UUID object
     */
    @Named("stringToUuid")
    default UUID stringToUuid(String uuidString) {
        return uuidString != null && !uuidString.trim().isEmpty() ? UUID.fromString(uuidString) : null;
    }
}