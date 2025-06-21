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

import org.greenbuttonalliance.espi.common.domain.usage.ApplicationInformationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Modern Spring Data JPA repository for ApplicationInformation entities.
 * Replaces the legacy ApplicationInformationRepositoryImpl with modern Spring Data patterns.
 */
@Repository
public interface ApplicationInformationRepository extends JpaRepository<ApplicationInformationEntity, Long> {

    /**
     * Find application information by UUID.
     */
    @Query("SELECT ai FROM ApplicationInformationEntity ai WHERE UPPER(ai.uuid) = UPPER(:uuid)")
    Optional<ApplicationInformationEntity> findByUuid(@Param("uuid") String uuid);

    /**
     * Find application information by client ID.
     */
    @Query("SELECT ai FROM ApplicationInformationEntity ai WHERE ai.clientId = :clientId")
    Optional<ApplicationInformationEntity> findByClientId(@Param("clientId") String clientId);

    /**
     * Find application information by data custodian ID.
     */
    @Query("SELECT ai FROM ApplicationInformationEntity ai WHERE ai.dataCustodianId = :dataCustodianId")
    Optional<ApplicationInformationEntity> findByDataCustodianId(@Param("dataCustodianId") String dataCustodianId);

    /**
     * Find all application information by kind.
     */
    @Query("SELECT ai FROM ApplicationInformationEntity ai WHERE ai.kind = :kind")
    List<ApplicationInformationEntity> findByKind(@Param("kind") String kind);

    /**
     * Find all application information IDs.
     */
    @Query("SELECT ai.id FROM ApplicationInformationEntity ai")
    List<Long> findAllIds();

    /**
     * Check if application exists by client ID.
     */
    @Query("SELECT COUNT(ai) > 0 FROM ApplicationInformationEntity ai WHERE ai.clientId = :clientId")
    boolean existsByClientId(@Param("clientId") String clientId);

    /**
     * Find applications by third party application status.
     */
    @Query("SELECT ai FROM ApplicationInformationEntity ai WHERE ai.thirdPartyApplicationStatus = :status")
    List<ApplicationInformationEntity> findByThirdPartyApplicationStatus(@Param("status") String status);

    /**
     * Find applications by data custodian application status.
     */
    @Query("SELECT ai FROM ApplicationInformationEntity ai WHERE ai.dataCustodianApplicationStatus = :status")
    List<ApplicationInformationEntity> findByDataCustodianApplicationStatus(@Param("status") String status);
}
