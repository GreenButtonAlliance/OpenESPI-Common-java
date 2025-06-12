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

package org.greenbuttonalliance.espi.common.domain.usage;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.greenbuttonalliance.espi.common.models.atom.LinkType;
import org.greenbuttonalliance.espi.common.service.EspiIdGeneratorService;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Enhanced base class for all ESPI identified objects using pure JPA/Hibernate annotations.
 * 
 * This entity provides:
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
public abstract class IdentifiedObjectEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key for database persistence.
     * Uses IDENTITY strategy for auto-increment support across databases.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    protected Long id;

    /**
     * NAESB ESPI compliant UUID identifier.
     * Generated using UUID5 based on href rel="self" values.
     * Stored as string for maximum database compatibility.
     */
    @NotNull
    @Column(name = "uuid", unique = true, nullable = false, length = 36)
    private String uuid;

    /**
     * Most significant bits of the UUID for efficient queries.
     * Extracted from the UUID for potential performance optimizations.
     */
    @Column(name = "uuid_msb")
    private Long uuidMostSignificantBits;

    /**
     * Least significant bits of the UUID for efficient queries.
     * Extracted from the UUID for potential performance optimizations.
     */
    @Column(name = "uuid_lsb")
    private Long uuidLeastSignificantBits;

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
     * Sets the UUID using a UUID object.
     * Automatically extracts and stores the most/least significant bits.
     * 
     * @param uuid the UUID to set
     */
    public void setUUID(UUID uuid) {
        if (uuid != null) {
            this.uuid = uuid.toString().toUpperCase();
            this.uuidMostSignificantBits = uuid.getMostSignificantBits();
            this.uuidLeastSignificantBits = uuid.getLeastSignificantBits();
            
            // Ensure self link is available for marshalling
            ensureSelfLink();
        }
    }

    /**
     * Gets the UUID as a UUID object.
     * 
     * @return the UUID object, or null if not set
     */
    public UUID getUUID() {
        return uuid != null ? UUID.fromString(uuid) : null;
    }

    /**
     * Gets the MRID (Model Resource Identifier) in ESPI format.
     * 
     * @return the MRID string with urn:uuid: prefix, or null if UUID not set
     */
    public String getMRID() {
        return uuid != null ? "urn:uuid:" + uuid : null;
    }

    /**
     * Sets the MRID from an ESPI-formatted string.
     * 
     * @param mrid the MRID string (with or without urn:uuid: prefix)
     */
    public void setMRID(String mrid) {
        if (mrid != null) {
            String cleanUuid = mrid.replace("urn:uuid:", "").toUpperCase();
            UUID parsedUuid = UUID.fromString(cleanUuid);
            setUUID(parsedUuid);
        }
    }

    /**
     * Gets a hashed string representation of the ID for href generation.
     * 
     * @return string representation of the UUID
     */
    public String getHashedId() {
        return uuid != null ? uuid : String.valueOf(id);
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
            setUUID(espiId);
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
        return String.format("/espi/1_1/resource/%s/%s", 
            getClass().getSimpleName().replace("Entity", ""), 
            getHashedId());
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
     * Merges data from another IdentifiedObjectEntity.
     * Updates timestamps, links, and description.
     * 
     * @param other the other entity to merge from
     */
    public void merge(IdentifiedObjectEntity other) {
        if (other != null) {
            this.description = other.description;
            this.published = other.published;
            this.selfLink = other.selfLink;
            this.upLink = other.upLink;
            
            // Merge related links (replace existing)
            this.relatedLinks.clear();
            if (other.relatedLinks != null) {
                this.relatedLinks.addAll(other.relatedLinks);
            }
        }
    }

    /**
     * Prepares the entity for persistence by ensuring required fields are set.
     */
    @PrePersist
    protected void prePersist() {
        if (published == null) {
            published = LocalDateTime.now();
        }
        ensureSelfLink();
        ensureUpLink();
    }

    /**
     * Updates timestamps before entity updates.
     */
    @PreUpdate 
    protected void preUpdate() {
        // updated timestamp is automatically handled by @UpdateTimestamp
    }

    /**
     * Manual setter for description field (Lombok issue workaround).
     * 
     * @param description the resource description
     */
    public void setDescription(String description) {
        this.description = description;
    }
}