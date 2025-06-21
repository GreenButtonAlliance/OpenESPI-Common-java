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

import org.greenbuttonalliance.espi.common.domain.legacy.ServiceDeliveryPoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ServiceDeliveryPointRepository extends JpaRepository<ServiceDeliveryPoint, Long> {

	// JpaRepository provides: save(), findById(), findAll(), deleteById(), etc.

	@Modifying
	@Transactional
	@Query("DELETE FROM ServiceDeliveryPoint s WHERE s.id = :id")
	void deleteById(@Param("id") Long id);

	@Query("SELECT s.id FROM ServiceDeliveryPoint s")
	List<Long> findAllIds();

	@Query("SELECT s FROM ServiceDeliveryPoint s WHERE s.name = :name")
	List<ServiceDeliveryPoint> findByName(@Param("name") String name);

	@Query("SELECT s FROM ServiceDeliveryPoint s WHERE s.tariffProfile = :tariffProfile")
	List<ServiceDeliveryPoint> findByTariffProfile(@Param("tariffProfile") String tariffProfile);

	@Query("SELECT s FROM ServiceDeliveryPoint s WHERE s.customerAgreement = :customerAgreement")
	List<ServiceDeliveryPoint> findByCustomerAgreement(@Param("customerAgreement") String customerAgreement);

	@Query("SELECT s FROM ServiceDeliveryPoint s WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :searchText, '%'))")
	List<ServiceDeliveryPoint> findByNameContaining(@Param("searchText") String searchText);

	// Additional utility methods
	@Query("SELECT COUNT(s) FROM ServiceDeliveryPoint s WHERE s.tariffProfile = :tariffProfile")
	Long countByTariffProfile(@Param("tariffProfile") String tariffProfile);

}