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
 * JPA entity for PnodeRef (Pricing Node Reference).
 * 
 * Represents a reference to a pricing node in the electrical grid used within UsagePoint.
 * Each UsagePoint can have multiple pricing node references for different time periods.
 */
@Entity
@Table(name = "pnode_refs")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class PnodeRefEntity implements Serializable {

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
     * Type of the aggregated pricing node.
     * Examples: "HUB", "LOAD_ZONE", "TRANSMISSION", "DISTRIBUTION"
     */
    @Column(name = "apnode_type", length = 64)
    private String apnodeType;

    /**
     * Reference to the pricing node identifier.
     * Examples: "CAISO_SP15_GEN_HUB", "PNODE_ZONE_EAST_HUB"
     */
    @Column(name = "ref", length = 256, nullable = false)
    private String ref;

    /**
     * Start effective date for the pricing node reference validity.
     * Stored as epoch seconds (TimeType in ESPI).
     */
    @Column(name = "start_effective_date")
    private Long startEffectiveDate;

    /**
     * End effective date for the pricing node reference validity.
     * Stored as epoch seconds (TimeType in ESPI). Null for indefinite validity.
     */
    @Column(name = "end_effective_date")
    private Long endEffectiveDate;

    /**
     * Usage point that owns this pricing node reference.
     * Many pricing node references can belong to one usage point.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usage_point_id", nullable = false)
    private UsagePointEntity usagePoint;

    /**
     * Constructor with all fields.
     */
    public PnodeRefEntity(String apnodeType, String ref, Long startEffectiveDate, Long endEffectiveDate, UsagePointEntity usagePoint) {
        this.apnodeType = apnodeType;
        this.ref = ref;
        this.startEffectiveDate = startEffectiveDate;
        this.endEffectiveDate = endEffectiveDate;
        this.usagePoint = usagePoint;
    }

    /**
     * Constructor with basic fields.
     */
    public PnodeRefEntity(String apnodeType, String ref, UsagePointEntity usagePoint) {
        this(apnodeType, ref, null, null, usagePoint);
    }

    /**
     * Checks if this pricing node reference is currently valid.
     * 
     * @return true if valid for current time
     */
    public boolean isValid() {
        long currentTime = System.currentTimeMillis() / 1000;
        return (startEffectiveDate == null || startEffectiveDate <= currentTime) && 
               (endEffectiveDate == null || endEffectiveDate >= currentTime);
    }

    /**
     * Gets display name for this pricing node reference.
     * 
     * @return formatted display name
     */
    public String getDisplayName() {
        if (apnodeType != null && ref != null) {
            return apnodeType + ":" + ref;
        }
        return ref != null ? ref : "Unknown";
    }
}