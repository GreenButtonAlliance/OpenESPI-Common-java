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

import org.greenbuttonalliance.espi.common.domain.legacy.Subscription;
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
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
	// JpaRepository provides: save(), findAll(), findById(), deleteById(), etc.
	// Note: merge() functionality is handled by save() in Spring Data JPA

	Optional<Subscription> findByHashedId(String hashedId);

	@Modifying
	@Transactional
	@Query("DELETE FROM Subscription s WHERE s.id = :id")
	void deleteById(@Param("id") Long id);

	Optional<Subscription> findByUuid(UUID uuid);

	@Query("SELECT s FROM Subscription s WHERE s.authorization.id = :authorizationId")
	Optional<Subscription> findByAuthorizationId(@Param("authorizationId") Long id);

	// Missing NamedQueries that need to be added:

	@Query("SELECT s.id FROM Subscription s")
	List<Long> findAllIds();

	@Query("SELECT s FROM Subscription s WHERE s.retailCustomer.id = :retailCustomerId")
	List<Subscription> findByRetailCustomerId(@Param("retailCustomerId") Long retailCustomerId);

	@Query("SELECT s FROM Subscription s WHERE s.applicationInformation.id = :applicationInformationId")
	List<Subscription> findByApplicationInformationId(@Param("applicationInformationId") Long applicationInformationId);

	@Query("SELECT s FROM Subscription s WHERE s.authorization IS NOT NULL AND s.authorization.status = 'ACTIVE'")
	List<Subscription> findActiveSubscriptions();

	@Query("SELECT DISTINCT s FROM Subscription s JOIN s.usagePoints up WHERE up.id = :usagePointId")
	List<Subscription> findByUsagePointId(@Param("usagePointId") Long usagePointId);
}
