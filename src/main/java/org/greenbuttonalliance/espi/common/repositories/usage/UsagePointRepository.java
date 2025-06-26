/*
 *
 *     Copyright (c) 2018-2025 Green Button Alliance, Inc.
 *
 *     Portions copyright (c) 2013-2018 EnergyOS.org
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 *
 */

package org.greenbuttonalliance.espi.common.repositories.usage;

import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Modern Spring Data JPA repository for UsagePoint entities.
 * Replaces the legacy UsagePointRepositoryImpl with modern Spring Data patterns.
 */
@Repository
public interface UsagePointRepository extends JpaRepository<UsagePointEntity, UUID> {

    /**
     * Find all usage points for a specific retail customer.
     */
    @Query("SELECT up FROM UsagePointEntity up WHERE up.retailCustomer.id = :retailCustomerId")
    List<UsagePointEntity> findAllByRetailCustomerId(@Param("retailCustomerId") Long retailCustomerId);

    /**
     * Find usage point by UUID.
     */
    @Query("SELECT up FROM UsagePointEntity up WHERE UPPER(up.uuid) = UPPER(:uuid)")
    Optional<UsagePointEntity> findByUuid(@Param("uuid") String uuid);

    /**
     * Find usage point by resource URI.
     */
    @Query("SELECT up FROM UsagePointEntity up WHERE up.uri = :uri")
    Optional<UsagePointEntity> findByResourceUri(@Param("uri") String uri);

    /**
     * Find usage point by related href.
     */
    @Query("SELECT up FROM UsagePointEntity up JOIN up.relatedLinks rl WHERE rl.href = :href")
    Optional<UsagePointEntity> findByRelatedHref(@Param("href") String href);

    /**
     * Find all usage points updated after a given timestamp.
     */
    @Query("SELECT up FROM UsagePointEntity up WHERE up.updated > :lastUpdate")
    List<UsagePointEntity> findAllUpdatedAfter(@Param("lastUpdate") Calendar lastUpdate);

    /**
     * Find all usage point IDs for a specific retail customer.
     */
    @Query("SELECT up.id FROM UsagePointEntity up WHERE up.retailCustomer.id = :retailCustomerId")
    List<Long> findAllIdsByRetailCustomerId(@Param("retailCustomerId") Long retailCustomerId);

    /**
     * Find all usage point IDs.
     */
    @Query("SELECT up.id FROM UsagePointEntity up")
    List<Long> findAllIds();

    /**
     * Check if usage point exists by UUID.
     */
    @Query("SELECT COUNT(up) > 0 FROM UsagePointEntity up WHERE UPPER(up.uuid) = UPPER(:uuid)")
    boolean existsByUuid(@Param("uuid") String uuid);

    /**
     * Delete usage point by UUID.
     */
    @Modifying
    @Transactional
    @Query("DELETE FROM UsagePointEntity up WHERE UPPER(up.uuid) = UPPER(:uuid)")
    void deleteByUuid(@Param("uuid") String uuid);
}
