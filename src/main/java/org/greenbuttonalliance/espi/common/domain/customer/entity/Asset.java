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

package org.greenbuttonalliance.espi.common.domain.customer.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Abstract base class for Asset without JAXB concerns.
 * 
 * Tangible resource of the utility, including power system equipment, various end devices, 
 * cabinets, buildings, etc. Asset description places emphasis on the physical characteristics 
 * of the equipment fulfilling that role.
 * 
 * This is a @MappedSuperclass that provides asset-specific fields but does not extend IdentifiedObject.
 * Actual ESPI resource entities that represent assets should extend IdentifiedObject directly.
 */
@MappedSuperclass
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public abstract class Asset implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Utility-specific classification of Asset and its subtypes, according to their corporate standards, 
     * practices, and existing IT systems (e.g., for management of assets, maintenance, work, outage, customers, etc.).
     */
    @Column(name = "type", length = 256)
    private String type;

    /**
     * Uniquely tracked commodity (UTC) number.
     */
    @Column(name = "utc_number", length = 256)
    private String utcNumber;

    /**
     * Serial number of this asset.
     */
    @Column(name = "serial_number", length = 256)
    private String serialNumber;

    /**
     * Lot number for this asset. Even for the same model and version number, many assets are manufactured in lots.
     */
    @Column(name = "lot_number", length = 256)
    private String lotNumber;

    /**
     * Purchase price of asset.
     */
    @Column(name = "purchase_price")
    private Long purchasePrice;

    /**
     * True if asset is considered critical for some reason (for example, a pole with critical attachments).
     */
    @Column(name = "critical")
    private Boolean critical;

    /**
     * Electronic address.
     */
    @Embedded
    private Organisation.ElectronicAddress electronicAddress;

    /**
     * Lifecycle dates for this asset.
     */
    @Embedded
    private LifecycleDate lifecycle;

    /**
     * Information on acceptance test.
     */
    @Embedded
    private AcceptanceTest acceptanceTest;

    /**
     * Condition of asset in inventory or at time of installation. Examples include new, rebuilt, 
     * overhaul required, other. Refer to inspection data for information on the most current condition of the asset.
     */
    @Column(name = "initial_condition", length = 256)
    private String initialCondition;

    /**
     * Whenever an asset is reconditioned, percentage of expected life for the asset when it was new; zero for new devices.
     */
    @Column(name = "initial_loss_of_life")
    private BigDecimal initialLossOfLife;

    /**
     * Status of this asset.
     */
    @Embedded
    private CustomerEntity.Status status;

    /**
     * Embeddable class for LifecycleDate
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class LifecycleDate {
        @Column(name = "installation_date")
        private java.time.OffsetDateTime installationDate;
        
        @Column(name = "manufactured_date")
        private java.time.OffsetDateTime manufacturedDate;
        
        @Column(name = "purchase_date")
        private java.time.OffsetDateTime purchaseDate;
        
        @Column(name = "received_date")
        private java.time.OffsetDateTime receivedDate;
        
        @Column(name = "retirement_date")
        private java.time.OffsetDateTime retirementDate;
        
        @Column(name = "removal_date")
        private java.time.OffsetDateTime removalDate;
    }

    /**
     * Embeddable class for AcceptanceTest
     */
    @Embeddable
    @Data
    @NoArgsConstructor
    public static class AcceptanceTest {
        @Column(name = "success")
        private Boolean success;
        
        @Column(name = "date_time")
        private java.time.OffsetDateTime dateTime;
        
        @Column(name = "type", length = 256)
        private String type;
    }
}