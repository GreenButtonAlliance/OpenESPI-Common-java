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

package org.greenbuttonalliance.espi.common.dto.atom;

import jakarta.xml.bind.annotation.*;

/**
 * Link DTO record for Atom protocol links.
 * 
 * Represents an Atom link with rel and href attributes for XML marshalling/unmarshalling.
 * Used in resource relationships and navigation.
 */
@XmlRootElement(name = "link", namespace = "http://www.w3.org/2005/Atom")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LinkType", namespace = "http://www.w3.org/2005/Atom")
public record LinkDto(
    
    @XmlAttribute(name = "rel")
    String rel,
    
    @XmlAttribute(name = "href")
    String href,
    
    @XmlAttribute(name = "type")
    String type
) {
    
    /**
     * Default constructor for JAXB.
     */
    public LinkDto() {
        this(null, null, null);
    }
    
    /**
     * Constructor for basic rel and href.
     */
    public LinkDto(String rel, String href) {
        this(rel, href, null);
    }
    
    /**
     * Creates a self link.
     */
    public static LinkDto self(String href) {
        return new LinkDto("self", href, "application/atom+xml");
    }
    
    /**
     * Creates an up link.
     */
    public static LinkDto up(String href) {
        return new LinkDto("up", href, "application/atom+xml");
    }
    
    /**
     * Creates a related link.
     */
    public static LinkDto related(String href) {
        return new LinkDto("related", href, "application/atom+xml");
    }
    
    /**
     * Creates an alternate link.
     */
    public static LinkDto alternate(String href, String type) {
        return new LinkDto("alternate", href, type);
    }
}