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

package org.greenbuttonalliance.espi.common.service.export;

import org.greenbuttonalliance.espi.common.dto.atom.AtomEntryDto;
import org.greenbuttonalliance.espi.common.dto.atom.AtomFeedDto;
import org.greenbuttonalliance.espi.common.dto.atom.AtomContentDto;
import org.greenbuttonalliance.espi.common.dto.atom.LinkDto;
import org.greenbuttonalliance.espi.common.mapper.GreenButtonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Modern generic export service for Green Button resources.
 * 
 * Replaces the legacy ExportServiceImpl with 40+ specific methods with a clean,
 * generic approach using MapStruct for Entity-DTO conversion and modern Java patterns.
 * 
 * This service handles the complete export pipeline:
 * 1. Entity → DTO conversion via MapStruct
 * 2. DTO → Atom Entry/Feed wrapping  
 * 3. JAXB marshalling to XML output stream
 */
@Service
public class AtomExportService {

    private final GreenButtonMapper mapper;
    private final Jaxb2Marshaller marshaller;

    @Autowired
    public AtomExportService(
        GreenButtonMapper mapper,
        @Qualifier("fragmentMarshaller") Jaxb2Marshaller marshaller
    ) {
        this.mapper = mapper;
        this.marshaller = marshaller;
    }

    /**
     * Exports a single resource as an Atom entry.
     * 
     * Generic method that replaces 20+ specific export methods from legacy ExportServiceImpl.
     * Uses MapStruct to convert Entity to DTO, then wraps in Atom entry format.
     * 
     * @param <E> the entity type
     * @param <D> the DTO type
     * @param entity the entity to export
     * @param mapperFunction function to convert entity to DTO using MapStruct
     * @param resourceName the resource name for Atom entry
     * @param selfHref the self link href
     * @param stream the output stream to write XML
     */
    public <E, D> void exportResource(
        E entity,
        Function<E, D> mapperFunction,
        String resourceName,
        String selfHref,
        OutputStream stream
    ) {
        if (entity == null) {
            throw new IllegalArgumentException("Entity cannot be null");
        }

        // Convert Entity to DTO using MapStruct
        D dto = mapperFunction.apply(entity);
        
        // Wrap DTO in Atom entry
        AtomEntryDto entry = createAtomEntry(dto, resourceName, selfHref);
        
        // Marshal to XML
        marshaller.marshal(entry, new StreamResult(stream));
    }

    /**
     * Gets the GreenButtonMapper for external access.
     * 
     * @return the mapper instance
     */
    public GreenButtonMapper getMapper() {
        return mapper;
    }

    /**
     * Exports a collection of resources as an Atom feed.
     * 
     * Generic method that replaces 20+ specific collection export methods.
     * Converts each entity to DTO, wraps in Atom entries, and creates an Atom feed.
     * 
     * @param <E> the entity type
     * @param <D> the DTO type
     * @param entities the entities to export
     * @param mapperFunction function to convert entity to DTO using MapStruct
     * @param resourceName the resource name for feed
     * @param feedTitle the feed title
     * @param feedId the feed ID
     * @param selfHref the feed self link
     * @param hrefGenerator function to generate href for each entity
     * @param stream the output stream to write XML
     */
    public <E, D> void exportResourceCollection(
        List<E> entities,
        Function<E, D> mapperFunction,
        String resourceName,
        String feedTitle,
        String feedId,
        String selfHref,
        Function<E, String> hrefGenerator,
        OutputStream stream
    ) {
        if (entities == null) {
            throw new IllegalArgumentException("Entities list cannot be null");
        }

        // Convert entities to DTOs and wrap in Atom entries
        List<AtomEntryDto> entries = entities.stream()
            .map(entity -> {
                D dto = mapperFunction.apply(entity);
                String entryHref = hrefGenerator.apply(entity);
                return createAtomEntry(dto, resourceName, entryHref);
            })
            .collect(Collectors.toList());

        // Create Atom feed
        AtomFeedDto feed = createAtomFeed(
            feedTitle,
            feedId,
            selfHref,
            entries
        );

        // Marshal to XML
        marshaller.marshal(feed, new StreamResult(stream));
    }

    /**
     * Convenience method for exporting UsagePoint entities.
     * 
     * @param entity the usage point entity
     * @param stream the output stream
     */
    public void exportUsagePoint(Object entity, OutputStream stream) {
        // TODO: Implement with proper type-safe casting when entity classes are fixed
        throw new UnsupportedOperationException("exportUsagePoint not yet implemented - requires entity class fixes");
    }

    public void exportMeterReading(Object entity, OutputStream stream) {
        // TODO: Implement with proper type-safe casting when entity classes are fixed
        throw new UnsupportedOperationException("exportMeterReading not yet implemented - requires entity class fixes");
    }

    public void exportIntervalBlock(Object entity, OutputStream stream) {
        // TODO: Implement with proper type-safe casting when entity classes are fixed
        throw new UnsupportedOperationException("exportIntervalBlock not yet implemented - requires entity class fixes");
    }

    public void exportUsageSummary(Object entity, OutputStream stream) {
        // TODO: Implement with proper type-safe casting when entity classes are fixed
        throw new UnsupportedOperationException("exportUsageSummary not yet implemented - requires entity class fixes");
    }

    public void exportElectricPowerQualitySummary(Object entity, OutputStream stream) {
        // TODO: Implement with proper type-safe casting when entity classes are fixed
        throw new UnsupportedOperationException("exportElectricPowerQualitySummary not yet implemented - requires entity class fixes");
    }

    public void exportCustomer(Object entity, OutputStream stream) {
        // TODO: Implement with proper type-safe casting when entity classes are fixed
        throw new UnsupportedOperationException("exportCustomer not yet implemented - requires entity class fixes");
    }

    /**
     * Creates an Atom entry wrapping a DTO resource.
     * 
     * @param dto the DTO resource
     * @param resourceName the resource name
     * @param selfHref the self link href
     * @return the Atom entry
     */
    private AtomEntryDto createAtomEntry(Object dto, String resourceName, String selfHref) {
        String entryId = generateEntryId(resourceName);
        String title = generateEntryTitle(resourceName);
        
        AtomContentDto content = new AtomContentDto("application/xml", dto);
        
        // Create self link
        LinkDto selfLink = new LinkDto(
            "self",
            selfHref,
            "application/atom+xml"
        );

        return new AtomEntryDto(
            entryId,
            title,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            List.of(selfLink),
            content
        );
    }

    /**
     * Creates an Atom feed containing multiple entries.
     * 
     * @param title the feed title
     * @param feedId the feed ID
     * @param selfHref the feed self link
     * @param entries the entries to include
     * @return the Atom feed
     */
    private AtomFeedDto createAtomFeed(
        String title,
        String feedId,
        String selfHref,
        List<AtomEntryDto> entries
    ) {
        // Create self link for feed
        LinkDto selfLink = new LinkDto(
            "self",
            selfHref,
            "application/atom+xml"
        );

        return new AtomFeedDto(
            feedId,
            title,
            OffsetDateTime.now(),
            OffsetDateTime.now(),
            List.of(selfLink),
            entries
        );
    }

    /**
     * Generates a unique entry ID for Atom entries.
     * 
     * @param resourceName the resource name
     * @return unique entry ID
     */
    private String generateEntryId(String resourceName) {
        return "urn:uuid:" + resourceName.toLowerCase() + "-" + UUID.randomUUID();
    }

    /**
     * Generates a human-readable entry title.
     * 
     * @param resourceName the resource name
     * @return entry title
     */
    private String generateEntryTitle(String resourceName) {
        return resourceName + " Entry";
    }

    // Href generation methods (would be extracted to a separate service in production)
    
    private String generateUsagePointHref(Object entity) {
        // Extract UUID from entity and generate href
        return "/espi/1_1/resource/UsagePoint/" + extractUuid(entity);
    }

    private String generateMeterReadingHref(Object entity) {
        return "/espi/1_1/resource/MeterReading/" + extractUuid(entity);
    }

    private String generateIntervalBlockHref(Object entity) {
        return "/espi/1_1/resource/IntervalBlock/" + extractUuid(entity);
    }

    private String generateUsageSummaryHref(Object entity) {
        return "/espi/1_1/resource/UsageSummary/" + extractUuid(entity);
    }

    private String generateElectricPowerQualitySummaryHref(Object entity) {
        return "/espi/1_1/resource/ElectricPowerQualitySummary/" + extractUuid(entity);
    }

    private String generateCustomerHref(Object entity) {
        return "/espi/1_1/resource/Customer/" + extractUuid(entity);
    }

    /**
     * Extracts UUID from entity using reflection (fallback method).
     * In production, this would use proper entity interfaces.
     * 
     * @param entity the entity
     * @return the UUID string
     */
    private String extractUuid(Object entity) {
        try {
            var method = entity.getClass().getMethod("getUuid");
            Object uuid = method.invoke(entity);
            return uuid != null ? uuid.toString() : "unknown";
        } catch (Exception e) {
            return "unknown-" + System.currentTimeMillis();
        }
    }
}