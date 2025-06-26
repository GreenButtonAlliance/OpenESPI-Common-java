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

package org.greenbuttonalliance.espi.common.service;

import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.greenbuttonalliance.espi.common.dto.atom.AtomFeedDto;
import org.greenbuttonalliance.espi.common.dto.atom.AtomEntryDto;

import java.io.OutputStream;
import java.util.List;
import java.util.UUID;

/**
 * Modern DTO-based export service using JAXB marshalling.
 * 
 * Provides methods to export entities as DTOs in Green Button XML format,
 * supporting both individual resources and collections wrapped in Atom feeds.
 */
public interface DtoExportService {

    /**
     * Exports a single usage point as an Atom entry.
     * 
     * @param usagePointId the usage point ID
     * @param stream output stream for XML
     */
    void exportUsagePointEntry(UUID usagePointId, OutputStream stream);

    /**
     * Exports multiple usage points as an Atom feed.
     * 
     * @param usagePointIds list of usage point IDs
     * @param stream output stream for XML
     */
    void exportUsagePointsFeedByIds(List<UUID> usagePointIds, OutputStream stream);

    /**
     * Exports a usage point entity as an Atom entry.
     * 
     * @param usagePoint the usage point entity
     * @param stream output stream for XML
     */
    void exportUsagePointEntry(UsagePointEntity usagePoint, OutputStream stream);

    /**
     * Exports usage point entities as an Atom feed.
     * 
     * @param usagePoints list of usage point entities
     * @param stream output stream for XML
     */
    void exportUsagePointsFeed(List<UsagePointEntity> usagePoints, OutputStream stream);

    /**
     * Exports any DTO as XML.
     * 
     * @param dto the DTO to export
     * @param stream output stream for XML
     */
    void exportDto(Object dto, OutputStream stream);

    /**
     * Creates an Atom feed from a list of entries.
     * 
     * @param title feed title
     * @param entries list of Atom entries
     * @return Atom feed DTO
     */
    AtomFeedDto createAtomFeed(String title, List<AtomEntryDto> entries);

    /**
     * Creates an Atom entry from a resource DTO.
     * 
     * @param title entry title
     * @param resource the resource DTO
     * @return Atom entry DTO
     */
    AtomEntryDto createAtomEntry(String title, Object resource);
}