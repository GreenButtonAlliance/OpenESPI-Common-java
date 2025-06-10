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

package org.greenbuttonalliance.espi.common.dto.usage;

import jakarta.xml.bind.annotation.*;

/**
 * ReadingType DTO record for JAXB XML marshalling/unmarshalling.
 * 
 * Represents the type and characteristics of a meter reading.
 * Placeholder implementation for basic structure.
 */
@XmlRootElement(name = "ReadingType", namespace = "http://naesb.org/espi")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReadingType", namespace = "http://naesb.org/espi")
public record ReadingTypeDto(
    
    @XmlTransient
    Long id,
    
    @XmlAttribute(name = "mRID")
    String uuid,
    
    @XmlElement(name = "description")
    String description
) {
    
    /**
     * Default constructor for JAXB.
     */
    public ReadingTypeDto() {
        this(null, null, null);
    }
}