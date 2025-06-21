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

/**
 * Pure JPA/Hibernate entity for Meter without JAXB concerns.
 * 
 * Physical asset that performs the metering role of the usage point. 
 * Used for measuring consumption and detection of events.
 */
@Entity
@Table(name = "meters", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"id"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class MeterEntity extends EndDeviceEntity {

    /**
     * Meter form designation per ANSI C12.10 or other applicable standard. 
     * An alphanumeric designation denoting the circuit arrangement for which the meter is applicable 
     * and its specific terminal arrangement.
     */
    @Column(name = "form_number", length = 256)
    private String formNumber;

    /**
     * All multipliers applied at this meter.
     * TODO: Create MeterMultiplierEntity and enable this relationship
     */
    // @OneToMany(mappedBy = "meter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private List<MeterMultiplierEntity> meterMultipliers;

    /**
     * [extension] Current interval length specified in seconds.
     */
    @Column(name = "interval_length")
    private Long intervalLength;
}