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

import org.greenbuttonalliance.espi.common.domain.usage.AggregatedNodeRefEntity;
import org.greenbuttonalliance.espi.common.domain.usage.PnodeRefEntity;
import org.greenbuttonalliance.espi.common.domain.usage.UsagePointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for AggregatedNodeRefEntity.
 * 
 * Provides CRUD operations and custom queries for aggregated node references.
 */
@Repository
public interface AggregatedNodeRefRepository extends JpaRepository<AggregatedNodeRefEntity, Long> {

    /**
     * Find all aggregated node references for a specific usage point.
     * 
     * @param usagePoint the usage point
     * @return list of aggregated node references
     */
    List<AggregatedNodeRefEntity> findByUsagePoint(UsagePointEntity usagePoint);

    /**
     * Find aggregated node references by usage point ID.
     * 
     * @param usagePointId the usage point ID
     * @return list of aggregated node references
     */
    List<AggregatedNodeRefEntity> findByUsagePointId(Long usagePointId);

    /**
     * Find aggregated node references by type.
     * 
     * @param anodeType the aggregated node type
     * @return list of aggregated node references
     */
    List<AggregatedNodeRefEntity> findByAnodeType(String anodeType);

    /**
     * Find aggregated node references by usage point and type.
     * 
     * @param usagePoint the usage point
     * @param anodeType the aggregated node type
     * @return list of aggregated node references
     */
    List<AggregatedNodeRefEntity> findByUsagePointAndAnodeType(UsagePointEntity usagePoint, String anodeType);

    /**
     * Find aggregated node references by reference identifier.
     * 
     * @param ref the reference identifier
     * @return list of aggregated node references
     */
    List<AggregatedNodeRefEntity> findByRef(String ref);

    /**
     * Find aggregated node references by associated pricing node reference.
     * 
     * @param pnodeRef the pricing node reference
     * @return list of aggregated node references
     */
    List<AggregatedNodeRefEntity> findByPnodeRef(PnodeRefEntity pnodeRef);

    /**
     * Find aggregated node references by pricing node reference ID.
     * 
     * @param pnodeRefId the pricing node reference ID
     * @return list of aggregated node references
     */
    List<AggregatedNodeRefEntity> findByPnodeRefId(Long pnodeRefId);

    /**
     * Find currently valid aggregated node references for a usage point.
     * 
     * @param usagePointId the usage point ID
     * @param currentTime current time in epoch seconds
     * @return list of valid aggregated node references
     */
    @Query("SELECT a FROM AggregatedNodeRefEntity a WHERE a.usagePoint.id = :usagePointId " +
           "AND (a.startEffectiveDate IS NULL OR a.startEffectiveDate <= :currentTime) " +
           "AND (a.endEffectiveDate IS NULL OR a.endEffectiveDate >= :currentTime)")
    List<AggregatedNodeRefEntity> findValidByUsagePointId(@Param("usagePointId") Long usagePointId, 
                                                         @Param("currentTime") Long currentTime);

    /**
     * Find currently valid aggregated node references by type.
     * 
     * @param anodeType the aggregated node type
     * @param currentTime current time in epoch seconds
     * @return list of valid aggregated node references
     */
    @Query("SELECT a FROM AggregatedNodeRefEntity a WHERE a.anodeType = :anodeType " +
           "AND (a.startEffectiveDate IS NULL OR a.startEffectiveDate <= :currentTime) " +
           "AND (a.endEffectiveDate IS NULL OR a.endEffectiveDate >= :currentTime)")
    List<AggregatedNodeRefEntity> findValidByAnodeType(@Param("anodeType") String anodeType, 
                                                      @Param("currentTime") Long currentTime);

    /**
     * Find aggregated node references with their pricing node references (fetch join).
     * 
     * @param usagePointId the usage point ID
     * @return list of aggregated node references with pricing nodes loaded
     */
    @Query("SELECT a FROM AggregatedNodeRefEntity a LEFT JOIN FETCH a.pnodeRef " +
           "WHERE a.usagePoint.id = :usagePointId")
    List<AggregatedNodeRefEntity> findByUsagePointIdWithPnodeRef(@Param("usagePointId") Long usagePointId);

    /**
     * Delete all aggregated node references for a usage point.
     * 
     * @param usagePoint the usage point
     * @return number of deleted records
     */
    Long deleteByUsagePoint(UsagePointEntity usagePoint);

    /**
     * Delete aggregated node references by usage point ID.
     * 
     * @param usagePointId the usage point ID
     * @return number of deleted records
     */
    Long deleteByUsagePointId(Long usagePointId);

    /**
     * Delete aggregated node references by pricing node reference.
     * 
     * @param pnodeRef the pricing node reference
     * @return number of deleted records
     */
    Long deleteByPnodeRef(PnodeRefEntity pnodeRef);
}