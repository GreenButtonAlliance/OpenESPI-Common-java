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

import org.energyos.espi.common.domain.MeterReading;
import org.energyos.espi.common.domain.UsagePoint;
import org.energyos.espi.common.models.atom.EntryType;
import org.energyos.espi.common.repositories.MeterReadingRepository;
import org.energyos.espi.common.utils.EntryTypeIterator;

public interface MeterReadingService {
	// TODO: likely deprecated

	MeterReading findByUUID(UUID uuid);

	List<MeterReading> findAllByUsagePoint(UsagePoint usagePoint);

	String feedFor(List<MeterReading> meterReadings);

	String entryFor(MeterReading meterReading);

	void associateByUUID(UsagePoint usagePoint, UUID uuid);

	// persistence management services
	public void setRepository(MeterReadingRepository repository);

	public void persist(MeterReading meterReading);

	// accessor services
	public MeterReading findById(Long retailCustomerId, Long usagePointId, Long meterReadingId);
	
	public MeterReading findById(long meterReadingId);
	
	public EntryTypeIterator findEntryTypeIterator(Long retailCustomerId, Long usagePointId);

	public EntryType findEntryType(Long retailCustomerId, Long usagePointId, Long meterReadingId);
	
	public EntryTypeIterator findEntryTypeIterator();
	
	public EntryType findEntryType(Long meterReadingId);

	public void delete(MeterReading meterReading);

	// import-exportResource services
	public MeterReading importResource(InputStream stream);

}
