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

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * JPA entity for AggregatedNodeRef (Aggregated Node Reference).
 * 
 * Represents a reference to an aggregated node in the electrical grid used within UsagePoint.
 * Each aggregated node reference includes an associated pricing node reference.
 */
@Entity
@Table(name = "aggregated_node_refs")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class AggregatedNodeRefEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key for database persistence.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @EqualsAndHashCode.Include
    private Long id;

    /**
     * Type of the aggregated node.
     * Examples: "LOAD_ZONE", "TRANSMISSION_ZONE", "DISTRIBUTION_ZONE", "MARKET_ZONE"
     */
    @Column(name = "anode_type", length = 64)
    private String anodeType;

    /**
     * Reference to the aggregated node identifier.
     * Examples: "CAISO_PGAE_VALLEY_AGG", "PATH26_AGGREGATE"
     */
    @Column(name = "ref", length = 256, nullable = false)
    private String ref;

    /**
     * Start effective date for the aggregated node reference validity.
     * Stored as epoch seconds (TimeType in ESPI).
     */
    @Column(name = "start_effective_date")
    private Long startEffectiveDate;

    /**
     * End effective date for the aggregated node reference validity.
     * Stored as epoch seconds (TimeType in ESPI). Null for indefinite validity.
     */
    @Column(name = "end_effective_date")
    private Long endEffectiveDate;

    /**
     * Associated pricing node reference for this aggregated node.
     * Each aggregated node references an underlying pricing node.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "pnode_ref_id")
    private PnodeRefEntity pnodeRef;

    /**
     * Usage point that owns this aggregated node reference.
     * Many aggregated node references can belong to one usage point.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_point_id", nullable = false)
    private UsagePointEntity usagePoint;

    /**
     * Constructor with all fields.
     */
    public AggregatedNodeRefEntity(String anodeType, String ref, Long startEffectiveDate, Long endEffectiveDate, 
                                 PnodeRefEntity pnodeRef, UsagePointEntity usagePoint) {
        this.anodeType = anodeType;
        this.ref = ref;
        this.startEffectiveDate = startEffectiveDate;
        this.endEffectiveDate = endEffectiveDate;
        this.pnodeRef = pnodeRef;
        this.usagePoint = usagePoint;
    }

    /**
     * Constructor with basic fields.
     */
    public AggregatedNodeRefEntity(String anodeType, String ref, PnodeRefEntity pnodeRef, UsagePointEntity usagePoint) {
        this(anodeType, ref, null, null, pnodeRef, usagePoint);
    }

    /**
     * Checks if this aggregated node reference is currently valid.
     * 
     * @return true if valid for current time
     */
    public boolean isValid() {
        long currentTime = System.currentTimeMillis() / 1000;
        return (startEffectiveDate == null || startEffectiveDate <= currentTime) && 
               (endEffectiveDate == null || endEffectiveDate >= currentTime);
    }

    /**
     * Gets display name for this aggregated node reference.
     * 
     * @return formatted display name
     */
    public String getDisplayName() {
        if (anodeType != null && ref != null) {
            return anodeType + ":" + ref;
        }
        return ref != null ? ref : "Unknown";
    }

    /**
     * Gets display name including the associated pricing node.
     * 
     * @return formatted display name with pricing node
     */
    public String getFullDisplayName() {
        String aggregatedDisplay = getDisplayName();
        if (pnodeRef != null) {
            return aggregatedDisplay + " -> " + pnodeRef.getDisplayName();
        }
        return aggregatedDisplay;
    }
}