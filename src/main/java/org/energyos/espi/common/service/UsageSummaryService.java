/*
 * Copyright 2013, 2014, 2015 EnergyOS.org
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.energyos.espi.common.service;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import org.energyos.espi.common.domain.UsageSummary;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.utils.EntryTypeIterator;

public interface UsageSummaryService {

	public UsageSummary findByUUID(UUID uuid);

	public List<UsageSummary> findAllByUsagePoint(
			UsagePoint usagePoint);

	public String feedFor(
			List<UsageSummary> usageSummarys);

	public String entryFor(UsageSummary usageSummary);

	public void associateByUUID(UsagePoint usagePoint, UUID uuid);

	public void persist(UsageSummary usageSummary);

	public UsageSummary findById(Long usageSummaryId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId,
			Long usageSummaryId);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId,
			Long usagePointId);

	public void add(UsageSummary usageSummary);

	public void delete(UsageSummary usageSummary);

	public UsageSummary importResource(InputStream stream);

}
