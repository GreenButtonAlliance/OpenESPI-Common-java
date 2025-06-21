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

import org.greenbuttonalliance.espi.common.domain.legacy.TimeConfiguration;
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
public interface TimeConfigurationRepository extends JpaRepository<TimeConfiguration, Long> {

	// JpaRepository provides: save(), findById(), findAll(), deleteById(), etc.

	@Modifying
	@Transactional
	@Query("DELETE FROM TimeConfiguration t WHERE t.id = :id")
	void deleteById(@Param("id") Long id);

	Optional<TimeConfiguration> findByUuid(UUID uuid);

	@Query("SELECT t.id FROM TimeConfiguration t")
	List<Long> findAllIds();

	@Query("SELECT usagePoint.localTimeParameters.id FROM UsagePoint usagePoint WHERE usagePoint.id = :usagePointId")
	List<Long> findAllIdsByUsagePointId(@Param("usagePointId") Long usagePointId);

	@Query("SELECT DISTINCT t.id FROM TimeConfiguration t")
	List<Long> findAllIdsByXpath0();

	@Query("SELECT DISTINCT t.id FROM TimeConfiguration t WHERE t.id = :o1Id")
	Optional<Long> findIdsByXpath(@Param("o1Id") Long o1Id);

}
