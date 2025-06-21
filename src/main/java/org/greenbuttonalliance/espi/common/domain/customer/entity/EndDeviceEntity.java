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
import org.greenbuttonalliance.espi.common.domain.common.IdentifiedObject;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Pure JPA/Hibernate entity for EndDevice without JAXB concerns.
 * 
 * Asset container that performs one or more end device functions. One type of end device is a meter 
 * which can perform metering, load management, connect/disconnect, accounting functions, etc. 
 * Some end devices, such as ones monitoring and controlling air conditioners, refrigerators, pool pumps 
 * may be connected to a meter. All end devices may have communication capability defined by the associated 
 * communication function(s). An end device may be owned by a consumer, a service provider, utility or otherwise.
 * 
 * There may be a related end device function that identifies a sensor or control point within a metering 
 * application or communications systems (e.g., water, gas, electricity).
 * Some devices may use an optical port that conforms to the ANSI C12.18 standard for communications.
 * 
 * This is an actual ESPI resource entity that extends IdentifiedObject directly.
 */
@Entity
@Table(name = "end_devices", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class EndDeviceEntity extends IdentifiedObject {

    // Asset fields (previously inherited from Asset superclass)
    
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
    private Asset.LifecycleDate lifecycle;

    /**
     * Information on acceptance test.
     */
    @Embedded
    private Asset.AcceptanceTest acceptanceTest;

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

    // AssetContainer fields (AssetContainer is simply an Asset that can contain other assets - no additional fields)

    // EndDevice specific fields

    /**
     * If true, there is no physical device. As an example, a virtual meter can be defined to aggregate 
     * the consumption for two or more physical meters. Otherwise, this is a physical hardware device.
     */
    @Column(name = "is_virtual")
    private Boolean isVirtual;

    /**
     * If true, this is a premises area network (PAN) device.
     */
    @Column(name = "is_pan")
    private Boolean isPan;

    /**
     * Installation code.
     */
    @Column(name = "install_code", length = 256)
    private String installCode;

    /**
     * Automated meter reading (AMR) or other communication system responsible for communications to this end device.
     */
    @Column(name = "amr_system", length = 256)
    private String amrSystem;
}