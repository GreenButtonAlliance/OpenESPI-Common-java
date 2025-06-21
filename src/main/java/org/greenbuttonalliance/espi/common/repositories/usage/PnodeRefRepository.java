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

import org.greenbuttonalliance.espi.common.domain.usage.PnodeRefEntity;
import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for PnodeRefEntity.
 * 
 * Provides CRUD operations and custom queries for pricing node references.
 */
@Repository
public interface PnodeRefRepository extends JpaRepository<PnodeRefEntity, Long> {

    /**
     * Find all pricing node references for a specific usage point.
     * 
     * @param usagePoint the usage point
     * @return list of pricing node references
     */
    List<PnodeRefEntity> findByUsagePoint(UsagePointEntity usagePoint);

    /**
     * Find pricing node references by usage point ID.
     * 
     * @param usagePointId the usage point ID
     * @return list of pricing node references
     */
    List<PnodeRefEntity> findByUsagePointId(Long usagePointId);

    /**
     * Find pricing node references by type.
     * 
     * @param apnodeType the pricing node type
     * @return list of pricing node references
     */
    List<PnodeRefEntity> findByApnodeType(String apnodeType);

    /**
     * Find pricing node references by usage point and type.
     * 
     * @param usagePoint the usage point
     * @param apnodeType the pricing node type
     * @return list of pricing node references
     */
    List<PnodeRefEntity> findByUsagePointAndApnodeType(UsagePointEntity usagePoint, String apnodeType);

    /**
     * Find pricing node references by reference identifier.
     * 
     * @param ref the reference identifier
     * @return list of pricing node references
     */
    List<PnodeRefEntity> findByRef(String ref);

    /**
     * Find currently valid pricing node references for a usage point.
     * 
     * @param usagePointId the usage point ID
     * @param currentTime current time in epoch seconds
     * @return list of valid pricing node references
     */
    @Query("SELECT p FROM PnodeRefEntity p WHERE p.usagePoint.id = :usagePointId " +
           "AND (p.startEffectiveDate IS NULL OR p.startEffectiveDate <= :currentTime) " +
           "AND (p.endEffectiveDate IS NULL OR p.endEffectiveDate >= :currentTime)")
    List<PnodeRefEntity> findValidByUsagePointId(@Param("usagePointId") Long usagePointId, 
                                                @Param("currentTime") Long currentTime);

    /**
     * Find currently valid pricing node references by type.
     * 
     * @param apnodeType the pricing node type
     * @param currentTime current time in epoch seconds
     * @return list of valid pricing node references
     */
    @Query("SELECT p FROM PnodeRefEntity p WHERE p.apnodeType = :apnodeType " +
           "AND (p.startEffectiveDate IS NULL OR p.startEffectiveDate <= :currentTime) " +
           "AND (p.endEffectiveDate IS NULL OR p.endEffectiveDate >= :currentTime)")
    List<PnodeRefEntity> findValidByApnodeType(@Param("apnodeType") String apnodeType, 
                                             @Param("currentTime") Long currentTime);

    /**
     * Delete all pricing node references for a usage point.
     * 
     * @param usagePoint the usage point
     * @return number of deleted records
     */
    Long deleteByUsagePoint(UsagePointEntity usagePoint);

    /**
     * Delete pricing node references by usage point ID.
     * 
     * @param usagePointId the usage point ID
     * @return number of deleted records
     */
    Long deleteByUsagePointId(Long usagePointId);
}