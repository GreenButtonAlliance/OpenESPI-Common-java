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
 * Pure JPA/Hibernate entity for Object without JAXB concerns.
 * 
 * Superclass of all object classes to allow extensions. Inheritance from Object 
 * provides an inherent extension mechanism permitting custom data to be included 
 * in a separate namespace.
 */
@Entity
@Table(name = "objects")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@NoArgsConstructor
@ToString
public class ObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Extensions can be stored as JSON or handled through inheritance.
     * For now, we'll use inheritance and handle extensions through subclassing.
     */
}