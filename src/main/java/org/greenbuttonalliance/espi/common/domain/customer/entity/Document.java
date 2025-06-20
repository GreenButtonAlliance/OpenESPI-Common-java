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
import java.time.OffsetDateTime;

/**
 * Abstract base class for Document types.
 * 
 * Parent class for different groupings of information collected and managed as a part of a business process.
 * It will frequently contain references to other objects, such as assets, people and power system resources.
 * This is a @MappedSuperclass that provides document-specific fields but does not extend IdentifiedObject.
 * Actual ESPI resource entities that represent documents should extend IdentifiedObject directly.
 */
@MappedSuperclass
@Data
@EqualsAndHashCode
@NoArgsConstructor
@ToString
public abstract class Document implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Date and time that this document was created.
     */
    @Column(name = "created_date_time")
    private OffsetDateTime createdDateTime;

    /**
     * Date and time that this document was last modified.
     */
    @Column(name = "last_modified_date_time")
    private OffsetDateTime lastModifiedDateTime;

    /**
     * Revision number for this document.
     */
    @Column(name = "revision_number", length = 256)
    private String revisionNumber;

    /**
     * Subject of this document, intended for this document to be found by a search engine.
     */
    @Column(name = "subject", length = 256)
    private String subject;

    /**
     * Title of this document.
     */
    @Column(name = "title", length = 256)
    private String title;

    /**
     * Type of this document.
     */
    @Column(name = "type", length = 256)
    private String type;
}