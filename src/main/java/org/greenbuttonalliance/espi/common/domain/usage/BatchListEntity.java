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
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Pure JPA/Hibernate entity for BatchList without JAXB concerns.
 * 
 * List of resource URIs that can be used to GET ESPI resources.
 * This entity supports batch operations by collecting multiple resource
 * URIs that can be processed together for efficient data retrieval
 * and manipulation operations.
 */
@Entity
@Table(name = "batch_lists", indexes = {
    @Index(name = "idx_batch_list_created_at", columnList = "created_at"),
    @Index(name = "idx_batch_list_resource_count", columnList = "resource_count")
})
@Data
public class BatchListEntity {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key for the batch list.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * List of resource URIs for batch processing.
     * Each URI points to an ESPI resource that can be retrieved or processed.
     */
    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
        name = "batch_list_resources", 
        joinColumns = @JoinColumn(name = "batch_list_id"),
        indexes = {
            @Index(name = "idx_batch_list_resources_batch_id", columnList = "batch_list_id"),
            @Index(name = "idx_batch_list_resources_uri", columnList = "resource_uri")
        }
    )
    @Column(name = "resource_uri", length = 512, nullable = false)
    @Size(max = 512, message = "Resource URI cannot exceed 512 characters")
    @NotNull(message = "Resource URI cannot be null")
    private List<String> resources = new ArrayList<>();

    /**
     * Cached count of resources for performance.
     * Updated automatically when resources are modified.
     */
    @Column(name = "resource_count")
    private Integer resourceCount = 0;

    /**
     * Timestamp when this batch list was created.
     * Automatically set when the entity is first persisted.
     */
    @Column(name = "created_at")
    private Long createdAt;

    /**
     * Timestamp when this batch list was last modified.
     * Updated automatically when resources are changed.
     */
    @Column(name = "updated_at")
    private Long updatedAt;

    /**
     * Optional description for this batch list.
     * Helpful for identifying the purpose or context of the batch.
     */
    @Column(name = "description", length = 256)
    @Size(max = 256, message = "Description cannot exceed 256 characters")
    private String description;

    /**
     * Constructor with description.
     * 
     * @param description the description of this batch list
     */
    public BatchListEntity(String description) {
        this.description = description;
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }

    /**
     * Default constructor required by JPA.
     */
    public BatchListEntity() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = this.createdAt;
    }

    /**
     * Constructor with initial resources.
     * 
     * @param resources the initial list of resource URIs
     */
    public BatchListEntity(List<String> resources) {
        this();
        if (resources != null) {
            this.resources = new ArrayList<>(resources);
            updateResourceCount();
        }
    }

    /**
     * Constructor with resources and description.
     * 
     * @param resources the initial list of resource URIs
     * @param description the description of this batch list
     */
    public BatchListEntity(List<String> resources, String description) {
        this(description);
        if (resources != null) {
            this.resources = new ArrayList<>(resources);
            updateResourceCount();
        }
    }

    /**
     * Gets the resources list, initializing if null.
     * 
     * @return the list of resource URIs
     */
    public List<String> getResources() {
        if (resources == null) {
            resources = new ArrayList<>();
        }
        return resources;
    }

    /**
     * Sets the resources list and updates the count.
     * 
     * @param resources the new list of resource URIs
     */
    public void setResources(List<String> resources) {
        this.resources = resources != null ? new ArrayList<>(resources) : new ArrayList<>();
        updateResourceCount();
        updateTimestamp();
    }

    /**
     * Adds a resource URI to the batch list.
     * 
     * @param resourceUri the resource URI to add
     * @return true if the resource was added, false if it already exists
     */
    public boolean addResource(String resourceUri) {
        if (resourceUri == null || resourceUri.trim().isEmpty()) {
            return false;
        }
        
        String trimmedUri = resourceUri.trim();
        if (!getResources().contains(trimmedUri)) {
            boolean added = getResources().add(trimmedUri);
            if (added) {
                updateResourceCount();
                updateTimestamp();
            }
            return added;
        }
        return false;
    }

    /**
     * Adds multiple resource URIs to the batch list.
     * 
     * @param resourceUris the resource URIs to add
     * @return the number of resources actually added (excluding duplicates)
     */
    public int addResources(List<String> resourceUris) {
        if (resourceUris == null || resourceUris.isEmpty()) {
            return 0;
        }
        
        int addedCount = 0;
        for (String uri : resourceUris) {
            if (addResource(uri)) {
                addedCount++;
            }
        }
        return addedCount;
    }

    /**
     * Removes a resource URI from the batch list.
     * 
     * @param resourceUri the resource URI to remove
     * @return true if the resource was removed, false if it didn't exist
     */
    public boolean removeResource(String resourceUri) {
        if (resourceUri == null || resources == null) {
            return false;
        }
        
        boolean removed = resources.remove(resourceUri.trim());
        if (removed) {
            updateResourceCount();
            updateTimestamp();
        }
        return removed;
    }

    /**
     * Removes multiple resource URIs from the batch list.
     * 
     * @param resourceUris the resource URIs to remove
     * @return the number of resources actually removed
     */
    public int removeResources(List<String> resourceUris) {
        if (resourceUris == null || resourceUris.isEmpty() || resources == null) {
            return 0;
        }
        
        int removedCount = 0;
        for (String uri : resourceUris) {
            if (removeResource(uri)) {
                removedCount++;
            }
        }
        return removedCount;
    }

    /**
     * Clears all resources from the batch list.
     */
    public void clearResources() {
        if (resources != null) {
            resources.clear();
            updateResourceCount();
            updateTimestamp();
        }
    }

    /**
     * Checks if the batch list contains a specific resource URI.
     * 
     * @param resourceUri the resource URI to check
     * @return true if the resource exists, false otherwise
     */
    public boolean containsResource(String resourceUri) {
        return resourceUri != null && resources != null && 
               resources.contains(resourceUri.trim());
    }

    /**
     * Checks if the batch list is empty.
     * 
     * @return true if no resources, false otherwise
     */
    public boolean isEmpty() {
        return resources == null || resources.isEmpty();
    }

    /**
     * Gets the number of resources in the batch list.
     * 
     * @return the count of resources
     */
    public int size() {
        return resources != null ? resources.size() : 0;
    }

    /**
     * Gets the cached resource count.
     * 
     * @return the cached count of resources
     */
    public Integer getResourceCount() {
        return resourceCount != null ? resourceCount : 0;
    }

    /**
     * Updates the cached resource count.
     */
    private void updateResourceCount() {
        this.resourceCount = size();
    }

    /**
     * Updates the last modified timestamp.
     */
    private void updateTimestamp() {
        this.updatedAt = System.currentTimeMillis();
    }

    /**
     * Gets unique resource URIs (removes duplicates).
     * 
     * @return set of unique resource URIs
     */
    public Set<String> getUniqueResources() {
        return resources != null ? 
            resources.stream().collect(Collectors.toSet()) : 
            Set.of();
    }

    /**
     * Removes duplicate resource URIs from the list.
     * 
     * @return the number of duplicates removed
     */
    public int removeDuplicates() {
        if (resources == null || resources.isEmpty()) {
            return 0;
        }
        
        int originalSize = resources.size();
        List<String> uniqueResources = resources.stream()
            .distinct()
            .collect(Collectors.toList());
        
        this.resources = uniqueResources;
        updateResourceCount();
        updateTimestamp();
        
        return originalSize - uniqueResources.size();
    }

    /**
     * Validates all resource URIs in the batch list.
     * 
     * @return list of invalid URIs (empty if all are valid)
     */
    public List<String> validateResourceUris() {
        if (resources == null || resources.isEmpty()) {
            return List.of();
        }
        
        List<String> invalidUris = new ArrayList<>();
        for (String uriString : resources) {
            if (!isValidUri(uriString)) {
                invalidUris.add(uriString);
            }
        }
        return invalidUris;
    }

    /**
     * Checks if a URI string is valid.
     * 
     * @param uriString the URI string to validate
     * @return true if valid, false otherwise
     */
    private boolean isValidUri(String uriString) {
        if (uriString == null || uriString.trim().isEmpty()) {
            return false;
        }
        
        try {
            URI uri = new URI(uriString.trim());
            return uri.getScheme() != null; // Basic validation - has scheme
        } catch (URISyntaxException e) {
            return false;
        }
    }

    /**
     * Filters resources by URI pattern.
     * 
     * @param pattern the pattern to match (supports wildcards with *)
     * @return list of matching resource URIs
     */
    public List<String> filterResourcesByPattern(String pattern) {
        if (pattern == null || resources == null) {
            return List.of();
        }
        
        String regexPattern = pattern.replace("*", ".*");
        return resources.stream()
            .filter(uri -> uri.matches(regexPattern))
            .collect(Collectors.toList());
    }

    /**
     * Gets resources that match a specific resource type.
     * 
     * @param resourceType the ESPI resource type (e.g., "UsagePoint", "MeterReading")
     * @return list of matching resource URIs
     */
    public List<String> getResourcesByType(String resourceType) {
        if (resourceType == null || resources == null) {
            return List.of();
        }
        
        return resources.stream()
            .filter(uri -> uri.contains("/" + resourceType + "/"))
            .collect(Collectors.toList());
    }

    /**
     * Gets a summary string for this batch list.
     * 
     * @return summary string with key information
     */
    public String getSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("Batch List ID: ").append(id);
        summary.append(" (").append(getResourceCount()).append(" resources)");
        
        if (description != null && !description.trim().isEmpty()) {
            summary.append(" - ").append(description);
        }
        
        if (createdAt != null) {
            summary.append(" created at ").append(new java.util.Date(createdAt));
        }
        
        return summary.toString();
    }

    /**
     * Gets statistics about the batch list.
     * 
     * @return statistics string
     */
    public String getStatistics() {
        StringBuilder stats = new StringBuilder();
        stats.append("Total resources: ").append(getResourceCount());
        stats.append(", Unique resources: ").append(getUniqueResources().size());
        
        List<String> invalidUris = validateResourceUris();
        stats.append(", Invalid URIs: ").append(invalidUris.size());
        
        if (createdAt != null && updatedAt != null) {
            stats.append(", Last modified: ");
            if (createdAt.equals(updatedAt)) {
                stats.append("never (created only)");
            } else {
                stats.append(new java.util.Date(updatedAt));
            }
        }
        
        return stats.toString();
    }

    /**
     * Validates the batch list.
     * 
     * @return true if valid, false otherwise
     */
    public boolean isValid() {
        // A batch list is valid if all its URIs are valid
        return validateResourceUris().isEmpty();
    }

    /**
     * Compares this batch list with another for business equality.
     * Two batch lists are considered equal if they contain the same resources
     * (order doesn't matter).
     * 
     * @param other the other batch list to compare
     * @return true if business equal, false otherwise
     */
    public boolean isBusinessEqual(BatchListEntity other) {
        if (other == null) {
            return false;
        }
        
        Set<String> thisResources = getUniqueResources();
        Set<String> otherResources = other.getUniqueResources();
        
        return thisResources.equals(otherResources);
    }

    /**
     * Pre-persist callback to set timestamps and update count.
     */
    @PrePersist
    protected void onCreate() {
        long now = System.currentTimeMillis();
        if (createdAt == null) {
            createdAt = now;
        }
        if (updatedAt == null) {
            updatedAt = now;
        }
        updateResourceCount();
    }

    /**
     * Pre-update callback to update timestamp and count.
     */
    @PreUpdate
    protected void onUpdate() {
        updatedAt = System.currentTimeMillis();
        updateResourceCount();
    }
}