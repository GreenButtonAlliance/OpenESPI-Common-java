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

/**
 * Pure JPA/Hibernate entity for StatementRef without JAXB concerns.
 * 
 * [extension] A sequence of references to a document associated with a Statement.
 * ESPI compliant with proper UUID identifiers and ATOM feed support.
 */
@Entity
@Table(name = "statement_refs", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"uuid"})
})
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@ToString(callSuper = true)
public class StatementRefEntity extends IdentifiedObject {

    /**
     * [extension] Name of document or file including filename extension if present.
     */
    @Column(name = "file_name", length = 512)
    private String fileName;

    /**
     * [extension] Document media type as published by IANA, see https://www.iana.org/assignments/media-types for more information.
     */
    @Column(name = "media_type", length = 256)
    private String mediaType;

    /**
     * [extension] URL used to access a representation of a statement, for example a bill image. 
     * Use CDATA or URL encoding to escape characters not allowed in XML.
     */
    @Column(name = "statement_url", nullable = false, length = 2048)
    private String statementURL;

    /**
     * Statement this reference belongs to
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "statement_id")
    private StatementEntity statement;
}