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

import org.greenbuttonalliance.espi.common.domain.legacy.LineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineItemRepository extends JpaRepository<LineItem, Long> {
	// JpaRepository provides: save(), findAll(), findById(), deleteById(), etc.
	// Note: merge() functionality is handled by save() in Spring Data JPA

	// All 12 original NamedQueries from LineItemEntity:

	@Query("SELECT li FROM LineItem li WHERE li.electricPowerUsageSummary.id = :electricPowerUsageSummaryId ORDER BY li.dateTime")
	List<LineItem> findByElectricPowerUsageSummaryId(@Param("electricPowerUsageSummaryId") Long electricPowerUsageSummaryId);

	@Query("SELECT li FROM LineItem li WHERE li.usageSummary.id = :usageSummaryId ORDER BY li.dateTime")
	List<LineItem> findByUsageSummaryId(@Param("usageSummaryId") Long usageSummaryId);

	@Query("SELECT li FROM LineItem li WHERE li.dateTime >= :startTime AND li.dateTime <= :endTime ORDER BY li.dateTime")
	List<LineItem> findByDateTimeRange(@Param("startTime") Long startTime, @Param("endTime") Long endTime);

	@Query("SELECT li FROM LineItem li WHERE li.amount >= :minAmount AND li.amount <= :maxAmount ORDER BY li.amount DESC")
	List<LineItem> findByAmountRange(@Param("minAmount") Long minAmount, @Param("maxAmount") Long maxAmount);

	@Query("SELECT li FROM LineItem li WHERE LOWER(li.note) LIKE LOWER(CONCAT('%', :searchText, '%')) ORDER BY li.dateTime")
	List<LineItem> findByNoteContaining(@Param("searchText") String searchText);

	@Query("SELECT li.id FROM LineItem li")
	List<Long> findAllIds();

	@Query("SELECT SUM(li.amount) FROM LineItem li WHERE li.electricPowerUsageSummary.id = :electricPowerUsageSummaryId")
	Long sumAmountsByElectricPowerUsageSummary(@Param("electricPowerUsageSummaryId") Long electricPowerUsageSummaryId);

	@Query("SELECT SUM(li.amount) FROM LineItem li WHERE li.usageSummary.id = :usageSummaryId")
	Long sumAmountsByUsageSummary(@Param("usageSummaryId") Long usageSummaryId);

	@Query("SELECT COUNT(li) FROM LineItem li WHERE li.electricPowerUsageSummary.id = :electricPowerUsageSummaryId")
	Long countByElectricPowerUsageSummary(@Param("electricPowerUsageSummaryId") Long electricPowerUsageSummaryId);

	@Query("SELECT COUNT(li) FROM LineItem li WHERE li.usageSummary.id = :usageSummaryId")
	Long countByUsageSummary(@Param("usageSummaryId") Long usageSummaryId);
}