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

package org.greenbuttonalliance.espi.common.domain.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.domain.legacy.atom.LinkType;
import org.greenbuttonalliance.espi.common.service.EspiIdGeneratorService;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Enhanced base class for all ESPI identified objects using pure JPA/Hibernate annotations.
 * 
 * This mapped superclass provides:
 * - NAESB ESPI compliant UUID5 identifiers (exceeds 48-bit requirement)
 * - Proper JPA entity mapping without JAXB concerns
 * - Lombok-generated boilerplate reduction
 * - Hibernate-managed timestamps
 * - Link management for ATOM feed generation
 */
@MappedSuperclass
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public abstract class IdentifiedObject implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * NAESB ESPI compliant UUID identifier as primary key.
     * Generated using UUID5 based on href rel="self" values.
     * Uses UUID type for maximum database compatibility and ESPI compliance.
     */
    @Id
    @Column(name = "id", columnDefinition = "uuid")
    @EqualsAndHashCode.Include
    protected UUID id;

    /**
     * Human-readable description of the resource.
     * Maps to ESPI IdentifiedObject.description field.
     */
    @Column(name = "description", length = 255)
    private String description;

    /**
     * Timestamp when the resource was created.
     * Automatically managed by Hibernate.
     */
    @CreationTimestamp
    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    /**
     * Timestamp when the resource was last updated.
     * Automatically managed by Hibernate.
     */
    @UpdateTimestamp
    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    /**
     * Timestamp when the resource was published.
     * Used for ATOM feed generation and synchronization.
     */
    @Column(name = "published")
    private LocalDateTime published;

    /**
     * Link to parent resource (up link).
     * Embedded LinkType for ATOM feed navigation.
     */
    @Embedded
    @Valid
    @AttributeOverrides({
        @AttributeOverride(name = "rel", column = @Column(name = "up_link_rel")),
        @AttributeOverride(name = "href", column = @Column(name = "up_link_href"))
    })
    private LinkType upLink;

    /**
     * Self-reference link.
     * Embedded LinkType for ATOM feed identification.
     */
    @Embedded
    @Valid
    @AttributeOverrides({
        @AttributeOverride(name = "rel", column = @Column(name = "self_link_rel")),
        @AttributeOverride(name = "href", column = @Column(name = "self_link_href"))
    })
    private LinkType selfLink;

    /**
     * Related links for this resource.
     * Stored as element collection for ATOM feed navigation.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "identified_object_related_links",
        joinColumns = @JoinColumn(name = "identified_object_id")
    )
    @AttributeOverrides({
        @AttributeOverride(name = "rel", column = @Column(name = "rel")),
        @AttributeOverride(name = "href", column = @Column(name = "href"))
    })
    private List<LinkType> relatedLinks = new ArrayList<>();

    /**
     * Sets the UUID identifier.
     * 
     * @param id the UUID to set as the primary key
     */
    public void setId(UUID id) {
        this.id = id;
        if (id != null) {
            // Ensure self link is available for marshalling
            ensureSelfLink();
        }
    }

    /**
     * Gets the UUID identifier.
     * 
     * @return the UUID primary key
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets a string representation of the ID for href generation.
     * 
     * @return string representation of the UUID
     */
    public String getHashedId() {
        return id != null ? id.toString() : null;
    }

    /**
     * Generates and sets a NAESB ESPI compliant UUID5 based on the resource's self href.
     * This method should be called when the self href is established.
     * 
     * @param idGeneratorService the ESPI ID generator service
     */
    public void generateEspiCompliantId(EspiIdGeneratorService idGeneratorService) {
        if (selfLink != null && selfLink.getHref() != null) {
            UUID espiId = idGeneratorService.generateEspiId(selfLink.getHref());
            setId(espiId);
        }
    }

    /**
     * Ensures that a self link exists for this resource.
     * Creates a default self link if none exists.
     */
    public void ensureSelfLink() {
        if (selfLink == null) {
            selfLink = new LinkType("self", generateDefaultSelfHref());
        }
    }

    /**
     * Ensures that an up link exists for this resource.
     * Creates a default up link if none exists.
     */
    public void ensureUpLink() {
        if (upLink == null) {
            upLink = new LinkType("up", generateDefaultUpHref());
        }
    }

    /**
     * Generates a default self href for this resource.
     * Subclasses can override this to provide resource-specific hrefs.
     * 
     * @return default self href string
     */
    protected String generateDefaultSelfHref() {
        String resourceName = getClass().getSimpleName().replace("Entity", "");
        String resourceId = getHashedId();
        return resourceId != null ? 
            String.format("/espi/1_1/resource/%s/%s", resourceName, resourceId) :
            String.format("/espi/1_1/resource/%s", resourceName);
    }

    /**
     * Generates a default up href for this resource.
     * Subclasses can override this to provide resource-specific parent hrefs.
     * 
     * @return default up href string
     */
    protected String generateDefaultUpHref() {
        return String.format("/espi/1_1/resource/%s", 
            getClass().getSimpleName().replace("Entity", ""));
    }

    /**
     * Adds a related link to this resource.
     * 
     * @param linkType the related link to add
     */
    public void addRelatedLink(LinkType linkType) {
        if (linkType != null) {
            relatedLinks.add(linkType);
        }
    }

    /**
     * Removes a related link from this resource.
     * 
     * @param linkType the related link to remove
     */
    public void removeRelatedLink(LinkType linkType) {
        relatedLinks.remove(linkType);
    }

    /**
     * Gets all related link hrefs as a list of strings.
     * 
     * @return list of href strings from related links
     */
    public List<String> getRelatedLinkHrefs() {
        return relatedLinks.stream()
            .map(LinkType::getHref)
            .filter(href -> href != null && !href.trim().isEmpty())
            .toList();
    }

    /**
     * Clears all related links.
     */
    public void clearRelatedLinks() {
        relatedLinks.clear();
    }

    /**
     * Merges this object with another IdentifiedObject.
     * Updates common fields while preserving the ID.
     * 
     * @param other the other IdentifiedObject to merge from
     */
    public void merge(IdentifiedObject other) {
        if (other != null) {
            // Preserve the existing ID - don't overwrite with other.getId()
            if (other.getDescription() != null) {
                this.setDescription(other.getDescription());
            }
            // Don't update created timestamp - it should remain immutable
            // Updated timestamp will be handled by @UpdateTimestamp
            if (other.getPublished() != null) {
                this.setPublished(other.getPublished());
            }
            // Merge links
            if (other.getUpLink() != null) {
                this.setUpLink(other.getUpLink());
            }
            if (other.getSelfLink() != null) {
                this.setSelfLink(other.getSelfLink());
            }
            if (other.getRelatedLinks() != null && !other.getRelatedLinks().isEmpty()) {
                this.getRelatedLinks().clear();
                this.getRelatedLinks().addAll(other.getRelatedLinks());
            }
        }
    }

    // Removed @PrePersist and @PreUpdate methods - Spring Boot handles lifecycle automatically

    /**
     * Manual setter for description field (Lombok issue workaround).
     * 
     * @param description the resource description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}