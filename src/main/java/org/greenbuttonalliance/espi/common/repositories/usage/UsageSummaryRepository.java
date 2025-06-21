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

import org.greenbuttonalliance.espi.common.domain.legacy.UsageSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Created by Donald F. Coffin on 06/28/2019 at 22:35
 */
@Repository
public interface UsageSummaryRepository extends JpaRepository<UsageSummary, Long> {
    // JpaRepository provides: save(), findById(), findAll(), deleteById(), etc.

    Optional<UsageSummary> findByUuid(UUID uuid);

    @Query("SELECT u.id FROM UsageSummary u")
    List<Long> findAllIds();

    @Modifying
    @Transactional
    @Query("DELETE FROM UsageSummary u WHERE u.id = :id")
    void deleteById(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM UsageSummary u WHERE u.uuid = :uuid")
    void deleteByUuid(@Param("uuid") UUID uuid);

    // Custom method for createOrReplaceByUUID - should be implemented in service layer

    @Query("SELECT u.id FROM UsageSummary u WHERE u.usagePoint.id = :usagePointId")
    List<Long> findAllIdsByUsagePointId(@Param("usagePointId") Long usagePointId);

    @Query("SELECT e.id FROM UsagePoint u, UsageSummary e WHERE u.retailCustomer.id = :o1Id AND e.usagePoint.id = :o2Id")
    List<Long> findAllIdsByXpath2(@Param("o1Id") Long o1Id, @Param("o2Id") Long o2Id);

    @Query("SELECT e.id FROM UsagePoint u, UsageSummary e WHERE u.retailCustomer.id = :o1Id AND e.usagePoint.id = :o2Id AND e.id = :o3Id")
    Optional<Long> findIdByXpath(@Param("o1Id") Long o1Id, @Param("o2Id") Long o2Id, @Param("o3Id") Long o3Id);

}
