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

package org.greenbuttonalliance.espi.common.repositories.customer;

import org.greenbuttonalliance.espi.common.domain.customer.entity.StatementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for Statement entities.
 * 
 * Manages billing statement data with document references and issue dates.
 */
@Repository
public interface StatementRepository extends JpaRepository<StatementEntity, Long> {

    /**
     * Find statement by UUID (case insensitive).
     */
    @Query("SELECT s FROM StatementEntity s WHERE UPPER(s.uuid) = UPPER(:uuid)")
    Optional<StatementEntity> findByUuid(@Param("uuid") String uuid);

    /**
     * Find statements issued after specified date.
     */
    @Query("SELECT s FROM StatementEntity s WHERE s.issueDateTime > :dateTime")
    List<StatementEntity> findByIssueDateTimeAfter(@Param("dateTime") OffsetDateTime dateTime);

    /**
     * Find statements issued before specified date.
     */
    @Query("SELECT s FROM StatementEntity s WHERE s.issueDateTime < :dateTime")
    List<StatementEntity> findByIssueDateTimeBefore(@Param("dateTime") OffsetDateTime dateTime);

    /**
     * Find statements issued between specified dates.
     */
    @Query("SELECT s FROM StatementEntity s WHERE s.issueDateTime BETWEEN :startDate AND :endDate")
    List<StatementEntity> findByIssueDateTimeBetween(@Param("startDate") OffsetDateTime startDate, @Param("endDate") OffsetDateTime endDate);

    /**
     * Find statements with document references.
     */
    @Query("SELECT s FROM StatementEntity s WHERE SIZE(s.statementRefs) > 0")
    List<StatementEntity> findStatementsWithReferences();

    /**
     * Find statements without document references.
     */
    @Query("SELECT s FROM StatementEntity s WHERE SIZE(s.statementRefs) = 0")
    List<StatementEntity> findStatementsWithoutReferences();

    /**
     * Find statements by description (from IdentifiedObject base class).
     */
    @Query("SELECT s FROM StatementEntity s WHERE UPPER(s.description) LIKE UPPER(CONCAT('%', :description, '%'))")
    List<StatementEntity> findByDescriptionContaining(@Param("description") String description);

    /**
     * Find recent statements (issued within last N days).
     */
    @Query("SELECT s FROM StatementEntity s WHERE s.issueDateTime > :cutoffDate ORDER BY s.issueDateTime DESC")
    List<StatementEntity> findRecentStatements(@Param("cutoffDate") OffsetDateTime cutoffDate);
}