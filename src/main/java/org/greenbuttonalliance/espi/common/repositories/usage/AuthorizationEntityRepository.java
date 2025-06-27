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

import org.greenbuttonalliance.espi.common.domain.usage.AuthorizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthorizationEntityRepository extends JpaRepository<AuthorizationEntity, UUID> {

	// JpaRepository provides: save(), findById(), findAll(), deleteById(), etc.

	List<AuthorizationEntity> findAllByRetailCustomerId(UUID retailCustomerId);

	@Query("SELECT a.id FROM AuthorizationEntity a WHERE a.applicationInformation.id = :applicationInformationId")
	List<UUID> findAllIdsByApplicationInformationId(@Param("applicationInformationId") UUID applicationInformationId);

	Optional<AuthorizationEntity> findByState(String state);

	@Query("SELECT a FROM AuthorizationEntity a WHERE a.scope = :scope AND a.retailCustomer.id = :retailCustomerId")
	Optional<AuthorizationEntity> findByScope(@Param("scope") String scope, @Param("retailCustomerId") UUID retailCustomerId);

	@Query("SELECT a.id FROM AuthorizationEntity a WHERE a.retailCustomer.id = :retailCustomerId")
	List<UUID> findAllIds(@Param("retailCustomerId") UUID retailCustomerId);

	@Query("SELECT a.id FROM AuthorizationEntity a")
	List<UUID> findAllIds();

	@Modifying
	@Transactional
	@Query("DELETE FROM AuthorizationEntity a WHERE a.id = :id")
	void deleteById(@Param("id") UUID id);

	Optional<AuthorizationEntity> findByAccessToken(String accessToken);

	Optional<AuthorizationEntity> findByRefreshToken(String refreshToken);

	@Query("SELECT a.id FROM AuthorizationEntity a WHERE a.thirdParty = :thirdParty AND a.scope LIKE :bulkId")
	List<UUID> findAllIdsByBulkId(@Param("thirdParty") String thirdParty, @Param("bulkId") String bulkId);

	@Query("SELECT a FROM AuthorizationEntity a WHERE a.resourceURI LIKE :uri")
	List<AuthorizationEntity> findByResourceUri(@Param("uri") String uri);

	@Query("SELECT a FROM AuthorizationEntity a WHERE a.expiresIn IS NOT NULL AND a.expiresIn < :currentTime")
	List<AuthorizationEntity> findExpiredAuthorizations(@Param("currentTime") Long currentTime);

	@Query("SELECT a FROM AuthorizationEntity a WHERE a.retailCustomer.id = :customerId AND a.applicationInformation.id = :applicationId AND (a.expiresIn IS NULL OR a.expiresIn > :currentTime)")
	List<AuthorizationEntity> findActiveByCustomerAndApplication(@Param("customerId") UUID customerId, @Param("applicationId") UUID applicationId, @Param("currentTime") Long currentTime);
}