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

/**
 * ESPI Usage DTOs for Green Button energy usage information exchange.
 * 
 * This package contains Data Transfer Objects (DTOs) for NAESB ESPI usage-related resources
 * including UsagePoint, MeterReading, IntervalBlock, and related energy consumption data.
 * These DTOs are used for JAXB XML marshalling/unmarshalling in Green Button implementations.
 */
@jakarta.xml.bind.annotation.XmlSchema(
    namespace = "http://naesb.org/espi",
    elementFormDefault = jakarta.xml.bind.annotation.XmlNsForm.QUALIFIED,
    xmlns = {
        @XmlNs(prefix = "espi", namespaceURI = "http://naesb.org/espi"),
        @XmlNs(prefix = "", namespaceURI = "http://www.w3.org/2005/Atom")
    }
)
package org.greenbuttonalliance.espi.common.dto.usage;

import jakarta.xml.bind.annotation.XmlNs;