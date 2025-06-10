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

package org.greenbuttonalliance.espi.common.service.customer;

import org.greenbuttonalliance.espi.common.domain.customer.entity.StatementEntity;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Service interface for Statement management.
 * 
 * Handles business logic for billing statement operations including bill amounts,
 * due dates, payment tracking, and statement status management.
 */
public interface StatementService {

    /**
     * Find all statements.
     */
    List<StatementEntity> findAll();

    /**
     * Find statement by ID.
     */
    Optional<StatementEntity> findById(Long id);

    /**
     * Find statement by UUID.
     */
    Optional<StatementEntity> findByUuid(String uuid);

    /**
     * Find statements issued after specified date.
     */
    List<StatementEntity> findByIssueDateTimeAfter(OffsetDateTime dateTime);

    /**
     * Find statements issued before specified date.
     */
    List<StatementEntity> findByIssueDateTimeBefore(OffsetDateTime dateTime);

    /**
     * Find statements issued between specified dates.
     */
    List<StatementEntity> findByIssueDateTimeBetween(OffsetDateTime startDate, OffsetDateTime endDate);

    /**
     * Find statements with document references.
     */
    List<StatementEntity> findStatementsWithReferences();

    /**
     * Find statements without document references.
     */
    List<StatementEntity> findStatementsWithoutReferences();

    /**
     * Find statements by description containing text.
     */
    List<StatementEntity> findByDescriptionContaining(String description);

    /**
     * Find recent statements (issued within last N days).
     */
    List<StatementEntity> findRecentStatements(OffsetDateTime cutoffDate);

    /**
     * Save statement.
     */
    StatementEntity save(StatementEntity statement);

    /**
     * Delete statement by ID.
     */
    void deleteById(Long id);

    /**
     * Update statement description.
     */
    StatementEntity updateDescription(Long id, String description);

    /**
     * Count total statements.
     */
    long countStatements();

    /**
     * Count statements with references.
     */
    long countStatementsWithReferences();

    /**
     * Count statements without references.
     */
    long countStatementsWithoutReferences();
}