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

import org.greenbuttonalliance.espi.common.domain.legacy.Authorization;
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
public interface AuthorizationRepository extends JpaRepository<Authorization, Long> {

	// JpaRepository provides: save(), findById(), findAll(), deleteById(), etc.

	List<Authorization> findAllByRetailCustomerId(Long retailCustomerId);

	@Query("SELECT a.id FROM Authorization a WHERE a.applicationInformation.id = :applicationInformationId")
	List<Long> findAllIdsByApplicationInformationId(@Param("applicationInformationId") Long applicationInformationId);

	Optional<Authorization> findByState(String state);

	@Query("SELECT a FROM Authorization a WHERE a.scope = :scope AND a.retailCustomer.id = :retailCustomerId")
	Optional<Authorization> findByScope(@Param("scope") String scope, @Param("retailCustomerId") Long retailCustomerId);

	@Query("SELECT a.id FROM Authorization a WHERE a.retailCustomer.id = :retailCustomerId")
	List<Long> findAllIds(@Param("retailCustomerId") Long retailCustomerId);

	Optional<Authorization> findByUuid(UUID uuid);

	@Query("SELECT a.id FROM Authorization a")
	List<Long> findAllIds();

	@Modifying
	@Transactional
	@Query("DELETE FROM Authorization a WHERE a.id = :id")
	void deleteById(@Param("id") Long id);

	@Modifying
	@Transactional
	@Query("DELETE FROM Authorization a WHERE a.uuid = :uuid")
	void deleteByUuid(@Param("uuid") UUID uuid);

	// Custom method for createOrReplaceByUUID - will need service layer implementation
	// Note: This should be implemented in a service class to avoid transactional issues

	Optional<Authorization> findByAccessToken(String accessToken);

	Optional<Authorization> findByRefreshToken(String refreshToken);

	@Query("SELECT a.id FROM Authorization a WHERE a.thirdParty = :thirdParty AND a.scope LIKE :bulkId")
	List<Long> findAllIdsByBulkId(@Param("thirdParty") String thirdParty, @Param("bulkId") String bulkId);

	// Missing NamedQueries that need to be added:
	
	@Query("SELECT a FROM Authorization a WHERE a.resourceURI LIKE :uri")
	List<Authorization> findByResourceUri(@Param("uri") String uri);

	@Query("SELECT a FROM Authorization a WHERE a.expiresIn IS NOT NULL AND a.expiresIn < :currentTime")
	List<Authorization> findExpiredAuthorizations(@Param("currentTime") Long currentTime);

	@Query("SELECT a FROM Authorization a WHERE a.retailCustomer.id = :customerId AND a.applicationInformation.id = :applicationId AND (a.expiresIn IS NULL OR a.expiresIn > :currentTime)")
	List<Authorization> findActiveByCustomerAndApplication(@Param("customerId") Long customerId, @Param("applicationId") Long applicationId, @Param("currentTime") Long currentTime);

}
