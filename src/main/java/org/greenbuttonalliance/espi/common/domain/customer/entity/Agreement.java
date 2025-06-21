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
import org.greenbuttonalliance.espi.common.domain.legacy.DateTimeInterval;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/**
 * Abstract base class for Agreement without JAXB concerns.
 * 
 * Formal agreement between two parties defining the terms and conditions for a set of services. 
 * The specifics of the services are, in turn, defined via one or more service agreements.
 */
@MappedSuperclass
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public abstract class Agreement extends Document {

    /**
     * Date this agreement was consummated among associated persons and/or organisations.
     */
    @Column(name = "sign_date")
    private OffsetDateTime signDate;

    /**
     * Date and time interval this agreement is valid (from going into effect to termination).
     */
    @Embedded
    private DateTimeInterval validityInterval;
}