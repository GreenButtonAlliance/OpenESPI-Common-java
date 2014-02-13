/*
 * Copyright 2013, 2014 EnergyOS.org
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

import org.energyos.espi.common.domain.RetailCustomer;
import org.energyos.espi.common.domain.TimeConfiguration;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.TimeConfigurationRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;
import org.energyos.espi.common.utils.ExportFilter;

public interface TimeConfigurationService {
	// TODO: likely deprecated
	TimeConfiguration findByUUID(UUID uuid);

	List<TimeConfiguration> findAllByRetailCustomer(RetailCustomer retailCustomer);

	String feedFor(List<TimeConfiguration> timeConfiguration);

	String entryFor(TimeConfiguration timeConfiguration);

	void associateByUUID(UsagePoint usagePoint, UUID uuid);

	TimeConfiguration importTimeConfiguration(InputStream stream);

	void deleteById(long timeConfigurationId);

	UsagePoint getUsagePoint();

	// persistence management services
	public void setRepository(TimeConfigurationRepository timeConfigurationRepository);

	public void persist(TimeConfiguration timeConfiguration);

	// accessor services
	public TimeConfiguration findById(Long timeConfigurationId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId, Long timeConfigurationId, ExportFilter params);

	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId, ExportFilter params);

	public void add(TimeConfiguration timeConfiguration);

	public void delete(TimeConfiguration timeConfiguration);

	// importResource services
	public TimeConfiguration importResource(InputStream stream);

}
